package com.example.quanlysach.dto.request;

import lombok.Data;

@Data
public class KhachHangRequest {
    private String maKhachHang;
    private String tenKhachHang;
    private String diaChi;
    private String email;
    private String sdt;
}
