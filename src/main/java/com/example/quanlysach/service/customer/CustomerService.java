package com.example.quanlysach.service.customer;

import com.example.quanlysach.dto.request.CustomerRequest;
import com.example.quanlysach.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAll();
    CustomerResponse createKhachHang(CustomerRequest khachHang);
    CustomerResponse updateKhachHang(Integer id, CustomerRequest khachHang);
    void deleteKhachHang(Integer id);

    boolean daCoThongTinKhachHang(Integer taiKhoanId);
}
