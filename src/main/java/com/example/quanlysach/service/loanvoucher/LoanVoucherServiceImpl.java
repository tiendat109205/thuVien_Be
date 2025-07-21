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
    private LoanVoucherRepository loanVoucherRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepoSitory accountRepoSitory;

    @Override
    public List<LoanVoucherResponse> getAll() {
        return loanVoucherRepository.findAll().stream().map(LoanVoucherResponse::new).collect(Collectors.toList());
    }

    @Override
    public LoanVoucherResponse create(LoanVoucherRequest loanVoucherRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Tìm tài khoản
        Account account = accountRepoSitory.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found"));
        System.out.println("aaaaaa"+account);
        // Tìm khách hàng gắn với tài khoản đó
        Customer customer = customerRepository.findByAccount_Id(account.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has no customer information"));
        // Kiểm tra đã có phiếu mượn đang hoạt động chưa
        Optional<LoanVoucher> existing = loanVoucherRepository.findByCustomerId(customer.getId());
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The customer has a loan voucher..");
        }
        // Tạo phiếu mượn mới
        LoanVoucher loanVoucher = new LoanVoucher();
        loanVoucher.setCustomer(customer);
        loanVoucher.setLoanVoucherCode(generateMaPhieuMuon());
        loanVoucher.setBorrowDate(new Date());
        loanVoucher.setStatus(true);
        LoanVoucher save = loanVoucherRepository.save(loanVoucher);
        return new LoanVoucherResponse(save);
    }

    private String generateMaPhieuMuon() {
        int numbers = 100 + new Random().nextInt(900);
        return "PM-" + numbers;
    }
    @Override
    public LoanVoucherResponse update(Integer id, LoanVoucherRequest loanVoucherRequest) {
        Customer customerId= customerRepository.findById(loanVoucherRequest.getCustomer()).orElseThrow(()->new RuntimeException("No customers found"));
        LoanVoucher loanVoucher = loanVoucherRepository.findById(id).orElseThrow(()->new RuntimeException("Can't find id"+id));
        loanVoucher.setCustomer(customerId);
        loanVoucher.setLoanVoucherCode(loanVoucherRequest.getLoanVoucherCode());
        loanVoucher.setBorrowDate(loanVoucherRequest.getBorrowDate());
        loanVoucher.setReturnDate(loanVoucherRequest.getReturnDate());
        loanVoucher.setStatus(loanVoucherRequest.getStatus());
        LoanVoucher update = loanVoucherRepository.save(loanVoucher);
        return new LoanVoucherResponse(update);
    }

    @Override
    public void delete(Integer id) {
        LoanVoucher loanVoucher = loanVoucherRepository.findById(id).orElseThrow(()->new RuntimeException("Can't find id"+id));
        loanVoucherRepository.deleteById(id);
    }
}
