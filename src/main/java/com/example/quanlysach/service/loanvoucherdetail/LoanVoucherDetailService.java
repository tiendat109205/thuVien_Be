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
    List<BookBorrowResponse> getSachDaMuonTheoKH(Integer khachHangId);
    List<CustomerBorrowResponse> getKhachDaMuonSach(Integer sachId);

    @Transactional
    void traSach(Integer chiTietId, Integer soLuongTra);
}
