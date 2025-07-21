package com.example.quanlysach.service.customer;

import com.example.quanlysach.dto.request.CustomerRequest;
import com.example.quanlysach.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAll();
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse updateCustomer(Integer id, CustomerRequest customerRequest);
    void deleteCustomer(Integer id);

    boolean checkInfomation(Integer accountId);
}
