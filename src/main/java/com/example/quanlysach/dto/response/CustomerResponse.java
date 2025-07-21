package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Integer id;
    private String customerCode;
    private Integer customer;
    private String customerName;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer accountId;

    public CustomerResponse(Integer id, String customerCode, String customerName, String address, String phoneNumber, String email, Integer accountId) {
        this.id = id;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accountId = accountId;
    }
}
