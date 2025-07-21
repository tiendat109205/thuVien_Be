package com.example.quanlysach.dto.response;

import com.example.quanlysach.entity.LoanVoucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanVoucherResponse {
    private Integer id;
    private String customer;
    private String loanVoucherCode;
    private Date borrowDate;
    private Date expiryDate;
    private Boolean status;

    public LoanVoucherResponse(LoanVoucher phieuMuon) {
        this.id = phieuMuon.getId();
        this.customer=phieuMuon.getCustomer().getCustomerName();
        this.loanVoucherCode=phieuMuon.getLoanVoucherCode();
        this.borrowDate=phieuMuon.getBorrowDate();
        this.expiryDate=phieuMuon.getReturnDate();
        this.status=phieuMuon.getStatus();
    }
}
