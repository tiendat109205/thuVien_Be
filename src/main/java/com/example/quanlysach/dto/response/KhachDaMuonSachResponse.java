package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachDaMuonSachResponse {
    private String maKhachHang;
    private String tenKhachHang;
    private String email;
    private String sdt;
    private Date ngayMuon;
    private Date ngayTra;
}
