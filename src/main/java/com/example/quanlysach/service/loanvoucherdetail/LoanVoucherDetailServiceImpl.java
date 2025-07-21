package com.example.quanlysach.service.loanvoucherdetail;

import com.example.quanlysach.dto.request.LoanVoucherDetailRequest;
import com.example.quanlysach.dto.request.BookBorrowRequest;
import com.example.quanlysach.dto.response.CustomerBorrowResponse;
import com.example.quanlysach.dto.response.LoanVoucherDetailResponse;
import com.example.quanlysach.dto.response.BookBorrowResponse;
import com.example.quanlysach.entity.Customer;
import com.example.quanlysach.entity.LoanVoucher;
import com.example.quanlysach.entity.LoanVoucherDetail;
import com.example.quanlysach.entity.Book;
import com.example.quanlysach.entity.Account;
import com.example.quanlysach.repository.CustomerRepository;
import com.example.quanlysach.repository.LoanVoucherDetailRepository;
import com.example.quanlysach.repository.LoanVoucherRepository;
import com.example.quanlysach.repository.BookRepository;
import com.example.quanlysach.repository.AccountRepoSitory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanVoucherDetailServiceImpl implements LoanVoucherDetailService {
    @Autowired
    private LoanVoucherDetailRepository phieuMuonChiTietRepository;
    @Autowired
    private LoanVoucherRepository phieuMuonRepository;
    @Autowired
    private BookRepository sachRepository;
    @Autowired
    private AccountRepoSitory taiKhoanRepoSitory;
    @Autowired
    private CustomerRepository khachHangRepository;

    @Override
    public List<LoanVoucherDetailResponse> getAll() {
        List<LoanVoucherDetail> chiTietList = phieuMuonChiTietRepository.findAll();
        List<LoanVoucherDetailResponse> responseList = new ArrayList<>();

        for (LoanVoucherDetail ct : chiTietList) {
            LoanVoucherDetailResponse res = new LoanVoucherDetailResponse();
            res.setId(ct.getId());
            res.setLoanVoucher(ct.getLoanVoucher().getId());
            res.setBook(ct.getBook().getId());
            res.setCustomerName(ct.getLoanVoucher().getCustomer().getCustomerName());
            res.setBookName(ct.getBook().getBookName());
            res.setBorrowDate(ct.getLoanVoucher().getBorrowDate());
            res.setExpiryDate(ct.getExpiryDate());

            responseList.add(res);
        }

        return responseList;
    }

    @Override
    public List<BookBorrowResponse> getSachDaMuonTheoKH(Integer khachHangId) {
        return phieuMuonChiTietRepository.getSachDaMuonTheoKhachHang(khachHangId);
    }

    @Override
    public List<CustomerBorrowResponse> getKhachDaMuonSach(Integer sachId) {
        return phieuMuonChiTietRepository.findKhachMuonTheoSach(sachId);
    }

    @Override
    @Transactional
    public List<LoanVoucherDetailResponse> create(LoanVoucherDetailRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account taiKhoan = taiKhoanRepoSitory.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy tài khoản"));
        boolean isUser = taiKhoan.getRole().equalsIgnoreCase("ROLE_USER");
        Customer khachHang = null;
        if (isUser) {
            khachHang = khachHangRepository.findByAccount_Id(taiKhoan.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tài khoản chưa có thông tin khách hàng"));
        }
        LoanVoucher phieuMuon = layPhieuMuon(request.getLoanVoucherId());
        //Chỉ chặn nếu là USER mà phiếu không thuộc về họ
        if (isUser && !phieuMuon.getCustomer().getId().equals(khachHang.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bạn không có quyền thao tác trên phiếu mượn này.");
        }
        phieuMuon.setBorrowDate(new Date());
        List<LoanVoucherDetailResponse> responses = new ArrayList<>();
        for (BookBorrowRequest item : request.getBookDetails()) {
            Book sach = xuLySachMuon(item);
            LoanVoucherDetail chiTiet = taoHoacCapNhatChiTiet(phieuMuon, sach, item.getQuantityBorrow(), request.getExpiryDate());
            LoanVoucherDetail saved = phieuMuonChiTietRepository.save(chiTiet);
            LoanVoucherDetailResponse response = new LoanVoucherDetailResponse(chiTiet.getId(),
                    phieuMuon.getId(),
                    sach.getId(),
                    phieuMuon.getCustomer().getCustomerName(),
                    sach.getBookName(),
                    phieuMuon.getBorrowDate(),
                    chiTiet.getExpiryDate(),
                    sach.getQuantity(),
                    chiTiet.getNumberBookBorrow());
            responses.add(response);
        }
        phieuMuon.setStatus(false);
        phieuMuonRepository.save(phieuMuon);
        return responses;
    }


    private LoanVoucher layPhieuMuon(Integer phieuMuonId) {
        return phieuMuonRepository.findById(phieuMuonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn"));
    }

    private Book xuLySachMuon(BookBorrowRequest item) {
        Book sach = sachRepository.findById(item.getBookId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách ID: " + item.getBookId()));

        if (sach.getQuantity() < item.getQuantityBorrow()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không đủ số lượng sách: " + sach.getBookName());
        }

        sach.setQuantity(sach.getQuantity() - item.getQuantityBorrow());
        return sachRepository.save(sach);
    }

    private LoanVoucherDetail taoHoacCapNhatChiTiet(LoanVoucher phieuMuon, Book sach, int soLuong, Date ngayHetHan) {
        Optional<LoanVoucherDetail> optionalChiTiet =
                phieuMuonChiTietRepository.findByLoanVoucherIdAndBookId(phieuMuon.getId(), sach.getId());

        if (optionalChiTiet.isPresent()) {
            LoanVoucherDetail chiTiet = optionalChiTiet.get();
            chiTiet.setNumberBookBorrow(chiTiet.getNumberBookBorrow() + soLuong);
            chiTiet.setExpiryDate(ngayHetHan);
            return chiTiet;
        } else {
            LoanVoucherDetail chiTiet = new LoanVoucherDetail();
            chiTiet.setLoanVoucher(phieuMuon);
            chiTiet.setBook(sach);
            chiTiet.setExpiryDate(ngayHetHan);
            chiTiet.setNumberBookBorrow(soLuong);
            return chiTiet;
        }
    }


    @Transactional
    @Override
    public void traSach(Integer chiTietId, Integer soLuongTra) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account taiKhoan = taiKhoanRepoSitory.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy tài khoản"));
        // Kiểm tra nếu không phải admin → bắt buộc kiểm tra quyền sở hữu phiếu
        boolean isAdmin = taiKhoan.getRole().equalsIgnoreCase("ROLE_ADMIN");
        // Tìm chi tiết phiếu mượn
        LoanVoucherDetail chiTiet = phieuMuonChiTietRepository.findById(chiTietId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chi tiết phiếu mượn không tồn tại"));
        LoanVoucher phieuMuon = chiTiet.getLoanVoucher();
        // Nếu là user thường thì chỉ được trả phiếu mượn của mình
        if (!isAdmin) {
            Customer khachHang = khachHangRepository.findByAccount_Id(taiKhoan.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tài khoản chưa có thông tin khách hàng"));
            if (!phieuMuon.getCustomer().getId().equals(khachHang.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền trả sách cho phiếu mượn này.");
            }
        }
        //Cập nhật số lượng sách
        capNhatSoLuongSachSauKhiTra(chiTiet.getBook(), soLuongTra);
        //Xử lý chi tiết phiếu
        xuLyChiTietPhieuMuonSauKhiTra(chiTiet, soLuongTra);
        //Nếu không còn sách mượn → cập nhật trạng thái phiếu
        if (!phieuMuonChiTietRepository.existsByLoanVoucherId(phieuMuon.getId())) {
            phieuMuon.setStatus(true);
            phieuMuon.setReturnDate(new Date());
            phieuMuonRepository.save(phieuMuon);
        }
    }


    private void capNhatSoLuongSachSauKhiTra(Book sach, Integer soLuongTra) {
        sach.setQuantity(sach.getQuantity() + soLuongTra);
        sachRepository.save(sach);
    }

    private void xuLyChiTietPhieuMuonSauKhiTra(LoanVoucherDetail chiTiet, Integer soLuongTra) {
        if (soLuongTra < chiTiet.getNumberBookBorrow()) {
            chiTiet.setNumberBookBorrow(chiTiet.getNumberBookBorrow() - soLuongTra);
            phieuMuonChiTietRepository.save(chiTiet);
        } else {
            phieuMuonChiTietRepository.deleteById(chiTiet.getId());
        }
    }

}
