package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.SachRequest;
import com.example.quanlysach.dto.response.SachResponse;
import com.example.quanlysach.entity.Sach;
import com.example.quanlysach.entity.TheLoai;
import com.example.quanlysach.service.sach.SachService;
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

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/sach")
public class SachController {
    @Autowired
    SachService sachService;

    @GetMapping("/getAll")
    public List<SachResponse> getAllSach() {
        return sachService.getAllSach();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSach(@RequestBody SachRequest sach) {
        SachResponse s = sachService.createSach(sach);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSach(@PathVariable Integer id, @RequestBody SachRequest sach) {
        SachResponse s = sachService.updateSach(id,sach);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSach(@PathVariable Integer id) {
        sachService.deleteSach(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
