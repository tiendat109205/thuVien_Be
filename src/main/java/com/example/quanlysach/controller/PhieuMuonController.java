package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.PhieuMuonRequest;
import com.example.quanlysach.dto.response.PhieuMuonResponse;
import com.example.quanlysach.entity.PhieuMuon;
import com.example.quanlysach.repository.PhieuMuonRepository;
import com.example.quanlysach.service.phieumuon.PhieuMuonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createPhieuMuon(@RequestBody PhieuMuonRequest request) {
        try {
            PhieuMuonResponse response = phieuMuonService.create(request);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message", ex.getReason()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi không xác định xảy ra"));
        }
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
