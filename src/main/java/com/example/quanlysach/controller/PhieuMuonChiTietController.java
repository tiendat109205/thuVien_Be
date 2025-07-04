package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.PhieuMuonChiTietRequest;
import com.example.quanlysach.dto.response.KhachDaMuonSachResponse;
import com.example.quanlysach.dto.response.PhieuMuonChiTietResponse;
import com.example.quanlysach.dto.response.SachDaMuonResponse;
import com.example.quanlysach.service.phieumuonchitiet.PhieuMuonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/phieu-muon-chi-tiet")
public class PhieuMuonChiTietController {
    @Autowired
    private PhieuMuonChiTietService phieuMuonChiTietService;

    @GetMapping("/getAll")
    public ResponseEntity<List<PhieuMuonChiTietResponse>> getAll() {
        return ResponseEntity.ok(phieuMuonChiTietService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody PhieuMuonChiTietRequest request) {
        return ResponseEntity.ok(phieuMuonChiTietService.create(request));
    }

    @GetMapping("/sach-da-muon/{khachHangId}")
    public ResponseEntity<List<SachDaMuonResponse>> getSachDaMuon(@PathVariable Integer khachHangId) {
        return ResponseEntity.ok(phieuMuonChiTietService.getSachDaMuonTheoKH(khachHangId));
    }
    @GetMapping("/khach-muon-sach/{sachId}")
    public ResponseEntity<List<KhachDaMuonSachResponse>> getKhachMuonSach(@PathVariable Integer sachId) {
        return ResponseEntity.ok(phieuMuonChiTietService.getKhachDaMuonSach(sachId));
    }
    @PutMapping("/tra-sach/{chiTietId}")
    public ResponseEntity<?> traSach(@PathVariable Integer chiTietId, @RequestParam Integer soLuongTra) {
        phieuMuonChiTietService.traSach(chiTietId, soLuongTra);
        return ResponseEntity.ok("Trả sách thành công");
    }

}
