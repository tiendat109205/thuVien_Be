package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuMuonChiTietResponse {
    private Integer id;
    private Integer phieuMuon;
    private Integer sach;
    private String tenKhachHang;
    private String tenSach;
    private Date ngayMuon;
    private Date ngayHetHan;
    private Integer soLuong;
    private Integer soLuongSachMuon;

}
