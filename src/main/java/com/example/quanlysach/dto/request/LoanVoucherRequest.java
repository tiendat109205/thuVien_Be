package com.example.quanlysach.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanVoucherRequest {
    private Integer customer;
    private String loanVoucherCode;
    private Date borrowDate;
    private Date returnDate;
    private Boolean status;
}
