package com.example.quanlysach.service.phieumuon;

import com.example.quanlysach.dto.request.PhieuMuonRequest;
import com.example.quanlysach.dto.response.PhieuMuonResponse;
import com.example.quanlysach.entity.KhachHang;
import com.example.quanlysach.entity.PhieuMuon;
import com.example.quanlysach.repository.KhachHangRepository;
import com.example.quanlysach.repository.PhieuMuonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhieuMuonServiceImpl implements PhieuMuonService {
    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;
    @Override
    public List<PhieuMuonResponse> getAll() {
        return phieuMuonRepository.findAll().stream().map(PhieuMuonResponse::new).collect(Collectors.toList());
    }

    @Override
    public PhieuMuonResponse create(PhieuMuonRequest phieuMuonRequest) {
        KhachHang khId= khachHangRepository.findById(phieuMuonRequest.getKhachHang()).orElseThrow(()->new RuntimeException("Khong tim thay khach hang"));
        PhieuMuon phieuMuon = new PhieuMuon();
        phieuMuon.setKhachHang(khId);
        phieuMuon.setMaPhieuMuon(phieuMuonRequest.getMaPhieuMuon());
        phieuMuon.setNgayMuon(phieuMuonRequest.getNgayMuon());
        phieuMuon.setNgayTra(phieuMuonRequest.getNgayTra());
        phieuMuon.setTrangThai(true);
        PhieuMuon save = phieuMuonRepository.save(phieuMuon);
        return new PhieuMuonResponse(save);
    }

    @Override
    public PhieuMuonResponse update(Integer id, PhieuMuonRequest phieuMuonRequest) {
        KhachHang khId= khachHangRepository.findById(phieuMuonRequest.getKhachHang()).orElseThrow(()->new RuntimeException("Khong tim thay khach hang"));
        PhieuMuon pm = phieuMuonRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id"+id));
        pm.setKhachHang(khId);
        pm.setMaPhieuMuon(phieuMuonRequest.getMaPhieuMuon());
        pm.setNgayMuon(phieuMuonRequest.getNgayMuon());
        pm.setNgayTra(phieuMuonRequest.getNgayTra());
        pm.setTrangThai(phieuMuonRequest.getTrangThai());
        PhieuMuon update = phieuMuonRepository.save(pm);
        return new PhieuMuonResponse(update);
    }

    @Override
    public void delete(Integer id) {
        PhieuMuon pm = phieuMuonRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id"+id));
        phieuMuonRepository.deleteById(id);
    }
}
