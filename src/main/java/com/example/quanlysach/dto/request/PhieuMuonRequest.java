package com.example.quanlysach.dto.request;

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
public class PhieuMuonRequest {
    private Integer khachHang;
    private String maPhieuMuon;
    private Date ngayMuon;
    private Date ngayTra;
    private Boolean trangThai;
}
