package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SachDaMuonResponse {
    private Integer id;
    private String maSach;
    private String tenSach;
    private String tacGia;
    private String nhaXuatBan;
    private Date ngayMuon;
    private Date ngayHetHan;
    private Integer soLuongSachMuon;
}
