package com.example.quanlysach.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhieuMuonChiTietRequest {
    private Integer phieuMuonId;
    private Date ngayHetHan;
    private List<SachMuonRequest> sachChiTiet;
}
