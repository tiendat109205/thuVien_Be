package com.example.quanlysach.service.phieumuonchitiet;

import com.example.quanlysach.dto.request.PhieuMuonChiTietRequest;
import com.example.quanlysach.dto.response.KhachDaMuonSachResponse;
import com.example.quanlysach.dto.response.PhieuMuonChiTietResponse;
import com.example.quanlysach.dto.response.SachDaMuonResponse;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;

public interface PhieuMuonChiTietService{
    List<PhieuMuonChiTietResponse> getAll();
    List<PhieuMuonChiTietResponse> create(PhieuMuonChiTietRequest request);
    List<SachDaMuonResponse> getSachDaMuonTheoKH(Integer khachHangId);
    List<KhachDaMuonSachResponse> getKhachDaMuonSach(Integer sachId);

    @Transactional
    void traSach(Integer chiTietId, Integer soLuongTra);
}
