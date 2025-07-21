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
    private LoanVoucherDetailRepository loanVoucherDetailRepository;
    @Autowired
    private LoanVoucherRepository loanVoucherRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AccountRepoSitory accountRepoSitory;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<LoanVoucherDetailResponse> getAll() {
        List<LoanVoucherDetail> detailList = loanVoucherDetailRepository.findAll();
        List<LoanVoucherDetailResponse> responseList = new ArrayList<>();

        for (LoanVoucherDetail ct : detailList) {
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
    public List<BookBorrowResponse> getBookBorrow(Integer customerId) {
        return loanVoucherDetailRepository.getBookBorrow(customerId);
    }

    @Override
    public List<CustomerBorrowResponse> getCustomerBorrow(Integer bookId) {
        return loanVoucherDetailRepository.getCustomerBorrow(bookId);
    }

    @Override
    @Transactional
    public List<LoanVoucherDetailResponse> create(LoanVoucherDetailRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepoSitory.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found"));
        boolean isUser = account.getRole().equalsIgnoreCase("ROLE_USER");
        Customer customer = null;
        if (isUser) {
            customer = customerRepository.findByAccount_Id(account.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has no customer information"));
        }
        LoanVoucher loanVoucher = getLoanVoucher(request.getLoanVoucherId());
        //Chỉ chặn nếu là USER mà phiếu không thuộc về họ
        if (isUser && !loanVoucher.getCustomer().getId().equals(customer.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have permission to operate on this loan ticket..");
        }
        loanVoucher.setBorrowDate(new Date());
        List<LoanVoucherDetailResponse> responses = new ArrayList<>();
        for (BookBorrowRequest item : request.getBookDetails()) {
            Book book = bookBoroww(item);
            LoanVoucherDetail detail = createOrUpdate(loanVoucher, book, item.getQuantityBorrow(), request.getExpiryDate());
            LoanVoucherDetail saved = loanVoucherDetailRepository.save(detail);
            LoanVoucherDetailResponse response = new LoanVoucherDetailResponse(detail.getId(),
                    loanVoucher.getId(),
                    book.getId(),
                    loanVoucher.getCustomer().getCustomerName(),
                    book.getBookName(),
                    loanVoucher.getBorrowDate(),
                    detail.getExpiryDate(),
                    book.getQuantity(),
                    detail.getNumberBookBorrow());
            responses.add(response);
        }
        loanVoucher.setStatus(false);
        loanVoucherRepository.save(loanVoucher);
        return responses;
    }


    private LoanVoucher getLoanVoucher(Integer loanVoucherId) {
        return loanVoucherRepository.findById(loanVoucherId)
                .orElseThrow(() -> new RuntimeException("No loan slip found"));
    }

    private Book bookBoroww(BookBorrowRequest item) {
        Book book = bookRepository.findById(item.getBookId())
                .orElseThrow(() -> new RuntimeException("No books found ID: " + item.getBookId()));

        if (book.getQuantity() < item.getQuantityBorrow()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough books: " + book.getBookName());
        }

        book.setQuantity(book.getQuantity() - item.getQuantityBorrow());
        return bookRepository.save(book);
    }

    private LoanVoucherDetail createOrUpdate(LoanVoucher loanVoucher, Book book, int quantity, Date expiryDate) {
        Optional<LoanVoucherDetail> optionalDetail =
                loanVoucherDetailRepository.findByLoanVoucherIdAndBookId(loanVoucher.getId(), book.getId());

        if (optionalDetail.isPresent()) {
            LoanVoucherDetail detail = optionalDetail.get();
            detail.setNumberBookBorrow(detail.getNumberBookBorrow() + quantity);
            detail.setExpiryDate(expiryDate);
            return detail;
        } else {
            LoanVoucherDetail chiTiet = new LoanVoucherDetail();
            chiTiet.setLoanVoucher(loanVoucher);
            chiTiet.setBook(book);
            chiTiet.setExpiryDate(expiryDate);
            chiTiet.setNumberBookBorrow(quantity);
            return chiTiet;
        }
    }


    @Transactional
    @Override
    public void returnBook(Integer id, Integer quantity) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepoSitory.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account not found"));
        // Kiểm tra nếu không phải admin → bắt buộc kiểm tra quyền sở hữu phiếu
        boolean isAdmin = account.getRole().equalsIgnoreCase("ROLE_ADMIN");
        // Tìm chi tiết phiếu mượn
        LoanVoucherDetail detail = loanVoucherDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan details do not exist"));
        LoanVoucher loanVoucher = detail.getLoanVoucher();
        // Nếu là user thường thì chỉ được trả phiếu mượn của mình
        if (!isAdmin) {
            Customer customer = customerRepository.findByAccount_Id(account.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account has no customer information"));
            if (!loanVoucher.getCustomer().getId().equals(customer.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to return the book for this loan.");
            }
        }
        //Cập nhật số lượng sách
        returnQuanTityBook(detail.getBook(), quantity);
        //Xử lý chi tiết phiếu
        returnLoanVoucherDetail(detail, quantity);
        //Nếu không còn sách mượn → cập nhật trạng thái phiếu
        if (!loanVoucherDetailRepository.existsByLoanVoucherId(loanVoucher.getId())) {
            loanVoucher.setStatus(true);
            loanVoucher.setReturnDate(new Date());
            loanVoucherRepository.save(loanVoucher);
        }
    }

    private void returnQuanTityBook(Book book, Integer quantityBorrow) {
        book.setQuantity(book.getQuantity() + quantityBorrow);
        bookRepository.save(book);
    }

    private void returnLoanVoucherDetail(LoanVoucherDetail detail, Integer quantityBorrow) {
        if (quantityBorrow < detail.getNumberBookBorrow()) {
            detail.setNumberBookBorrow(detail.getNumberBookBorrow() - quantityBorrow);
            loanVoucherDetailRepository.save(detail);
        } else {
            loanVoucherDetailRepository.deleteById(detail.getId());
        }
    }

}
