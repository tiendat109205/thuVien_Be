package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.PhieuMuonRequest;
import com.example.quanlysach.dto.response.PhieuMuonResponse;
import com.example.quanlysach.entity.PhieuMuon;
import com.example.quanlysach.repository.PhieuMuonRepository;
import com.example.quanlysach.service.phieumuon.PhieuMuonService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/phieu-muon")
public class PhieuMuonController {
    @Autowired
    PhieuMuonService phieuMuonService;

    @GetMapping("/getAll")
    public List<PhieuMuonResponse> getAll() {
        return phieuMuonService.getAll();
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody PhieuMuonRequest pm) {
        PhieuMuonResponse add = phieuMuonService.create(pm);
        return ResponseEntity.ok(add);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PhieuMuonRequest pm) {
        PhieuMuonResponse update = phieuMuonService.update(id, pm);
        return ResponseEntity.ok(update);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        phieuMuonService.delete(id);
        return ResponseEntity.ok().build();
    }
}
