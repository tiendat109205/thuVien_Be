package com.example.quanlysach.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private Integer accountId;
    private String customerCode;
    private String customerName;
    private String address;
    private String email;
    private String phoneNumber;
}