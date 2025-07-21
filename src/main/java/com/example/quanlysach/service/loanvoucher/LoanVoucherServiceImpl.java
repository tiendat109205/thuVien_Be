package com.example.quanlysach.service.loanvoucher;

import com.example.quanlysach.dto.request.LoanVoucherRequest;
import com.example.quanlysach.dto.response.LoanVoucherResponse;
import com.example.quanlysach.entity.Customer;
import com.example.quanlysach.entity.LoanVoucher;
import com.example.quanlysach.entity.Account;
import com.example.quanlysach.repository.CustomerRepository;
import com.example.quanlysach.repository.LoanVoucherRepository;
import com.example.quanlysach.repository.AccountRepoSitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LoanVoucherServiceImpl implements LoanVoucherService {
    @Autowired
    private LoanVoucherRepository phieuMuonRepository;

    @Autowired
    private CustomerRepository khachHangRepository;
    @Autowired
    private AccountRepoSitory taiKhoanRepoSitory;

    @Override
    public List<LoanVoucherResponse> getAll() {
        return phieuMuonRepository.findAll().stream().map(LoanVoucherResponse::new).collect(Collectors.toList());
    }

    @Override
    public LoanVoucherResponse create(LoanVoucherRequest phieuMuonRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Tìm tài khoản
        Account taiKhoan = taiKhoanRepoSitory.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy tài khoản"));
        System.out.println("aaaaaa"+taiKhoan);
        // Tìm khách hàng gắn với tài khoản đó
        Customer khachHang = khachHangRepository.findByAccount_Id(taiKhoan.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tài khoản chưa có thông tin khách hàng"));
        // Kiểm tra đã có phiếu mượn đang hoạt động chưa
        Optional<LoanVoucher> existing = phieuMuonRepository.findByCustomerId(khachHang.getId());
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Khách hàng đã có phiếu mượn.");
        }
        // Tạo phiếu mượn mới
        LoanVoucher phieuMuon = new LoanVoucher();
        phieuMuon.setCustomer(khachHang);
        phieuMuon.setLoanVoucherCode(generateMaPhieuMuon());
        phieuMuon.setBorrowDate(new Date());
        phieuMuon.setStatus(true);
        LoanVoucher save = phieuMuonRepository.save(phieuMuon);
        return new LoanVoucherResponse(save);
    }

    private String generateMaPhieuMuon() {
        int numbers = 100 + new Random().nextInt(900);
        return "PM-" + numbers;
    }
    @Override
    public LoanVoucherResponse update(Integer id, LoanVoucherRequest phieuMuonRequest) {
        Customer khId= khachHangRepository.findById(phieuMuonRequest.getCustomer()).orElseThrow(()->new RuntimeException("Khong tim thay khach hang"));
        LoanVoucher pm = phieuMuonRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id"+id));
        pm.setCustomer(khId);
        pm.setLoanVoucherCode(phieuMuonRequest.getLoanVoucherCode());
        pm.setBorrowDate(phieuMuonRequest.getBorrowDate());
        pm.setReturnDate(phieuMuonRequest.getReturnDate());
        pm.setStatus(phieuMuonRequest.getStatus());
        LoanVoucher update = phieuMuonRepository.save(pm);
        return new LoanVoucherResponse(update);
    }

    @Override
    public void delete(Integer id) {
        LoanVoucher pm = phieuMuonRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id"+id));
        phieuMuonRepository.deleteById(id);
    }
}
