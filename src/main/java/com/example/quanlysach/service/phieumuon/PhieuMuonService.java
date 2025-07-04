package com.example.quanlysach.service.phieumuon;

import com.example.quanlysach.dto.request.PhieuMuonRequest;
import com.example.quanlysach.dto.response.PhieuMuonResponse;

import java.util.List;

public interface PhieuMuonService {
    List<PhieuMuonResponse> getAll();
    PhieuMuonResponse create(PhieuMuonRequest phieuMuonRequest);
    PhieuMuonResponse update(Integer id, PhieuMuonRequest phieuMuonRequest);
    void delete(Integer id);
}
