package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.KhachHangRequest;
import com.example.quanlysach.dto.response.KhachHangResponse;
import com.example.quanlysach.entity.KhachHang;
import com.example.quanlysach.service.khachhang.KhachHangService;
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
@RequestMapping("/api/khach-hang")
public class KhachHangController {
    @Autowired
    KhachHangService khachHangService;

    @GetMapping("/getAll")
    public List<KhachHangResponse> getAll() {
        return khachHangService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addKhachHang(@RequestBody KhachHangRequest khachHang) {
        KhachHangResponse add = khachHangService.createKhachHang(khachHang);
        return ResponseEntity.ok(add);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKhachHang(@RequestBody KhachHangRequest khachHang, @PathVariable Integer id) {
        KhachHangResponse update = khachHangService.updateKhachHang(id, khachHang);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteKhachHang(@PathVariable Integer id) {
        khachHangService.deleteKhachHang(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-thong-tin")
    public ResponseEntity<Boolean> checkThongTin(@RequestParam Integer taiKhoanId) {
        boolean exists = khachHangService.daCoThongTinKhachHang(taiKhoanId);
        return ResponseEntity.ok(exists);
    }
}
