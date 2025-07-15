package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangResponse {
    private Integer id;
    private String maKhachHang;
    private Integer khachHang;
    private String tenKhachHang;
    private String diaChi;
    private String email;
    private String sdt;
    private Integer taiKhoanid;

    public KhachHangResponse(Integer id, String maKhachHang, String tenKhachHang, String diaChi, String email, String sdt,Integer taiKhoanid) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.email = email;
        this.sdt = sdt;
        this.taiKhoanid = taiKhoanid;
    }
}
