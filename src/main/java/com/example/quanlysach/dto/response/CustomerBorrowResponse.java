package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBorrowResponse {
    private String customerCode;
    private String customerName;
    private String email;
    private String phoneNumber;
    private Date borrowDate;
    private Date returnDate;
}
