package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanVoucherDetailResponse {
    private Integer id;
    private Integer loanVoucher;
    private Integer book;
    private String customerName;
    private String bookName;
    private Date borrowDate;
    private Date expiryDate;
    private Integer quantity;
    private Integer numberBookBorrow;

}
