package com.example.quanlysach.service.loanvoucherdetail;

import com.example.quanlysach.dto.request.LoanVoucherDetailRequest;
import com.example.quanlysach.dto.response.CustomerBorrowResponse;
import com.example.quanlysach.dto.response.LoanVoucherDetailResponse;
import com.example.quanlysach.dto.response.BookBorrowResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface LoanVoucherDetailService {
    List<LoanVoucherDetailResponse> getAll();
    List<LoanVoucherDetailResponse> create(LoanVoucherDetailRequest request);
    List<BookBorrowResponse> getBookBorrow(Integer customerId);
    List<CustomerBorrowResponse> getCustomerBorrow(Integer bookId);

    @Transactional
    void returnBook(Integer id, Integer quantity);
}
