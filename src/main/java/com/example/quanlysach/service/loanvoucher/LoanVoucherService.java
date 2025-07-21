package com.example.quanlysach.service.loanvoucher;

import com.example.quanlysach.dto.request.LoanVoucherRequest;
import com.example.quanlysach.dto.response.LoanVoucherResponse;

import java.util.List;

public interface LoanVoucherService {
    List<LoanVoucherResponse> getAll();
    LoanVoucherResponse create(LoanVoucherRequest phieuMuonRequest);
    LoanVoucherResponse update(Integer id, LoanVoucherRequest phieuMuonRequest);
    void delete(Integer id);
}
