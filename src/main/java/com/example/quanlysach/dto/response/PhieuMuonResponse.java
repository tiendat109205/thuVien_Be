package com.example.quanlysach.dto.response;

import com.example.quanlysach.entity.PhieuMuon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhieuMuonResponse {
    private Integer id;
    private String khachHang;
    private String maPhieuMuon;
    private Date ngayMuon;
    private Date ngayHetHan;
    private Boolean trangThai;

    public PhieuMuonResponse(PhieuMuon phieuMuon) {
        this.id = phieuMuon.getId();
        this.khachHang=phieuMuon.getKhachHang().getTenKhachHang();
        this.maPhieuMuon=phieuMuon.getMaPhieuMuon();
        this.ngayMuon=phieuMuon.getNgayMuon();
        this.ngayHetHan=phieuMuon.getNgayTra();
        this.trangThai=phieuMuon.getTrangThai();
    }
}
