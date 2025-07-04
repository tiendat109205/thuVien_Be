package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.TheLoaiRequest;
import com.example.quanlysach.dto.response.TheLoaiResponse;
import com.example.quanlysach.entity.TheLoai;
import com.example.quanlysach.service.theloai.TheLoaiService;
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
@RequestMapping("/api/the-loai")
public class TheLoaiController {
    @Autowired
    TheLoaiService theLoaiService;

    @GetMapping("/getAll")
    public List<TheLoai> getAllTheLoai() {
        return theLoaiService.getAllTheLoai();
    }
    @PostMapping("/add")
    public ResponseEntity<?> addTheLoai(@RequestBody TheLoaiRequest theLoaiRequest) {
        TheLoaiResponse result = theLoaiService.createTheLoai(theLoaiRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTheLoai(@RequestBody TheLoaiRequest theLoaiRequest, @PathVariable Integer id) {
        TheLoaiResponse result = theLoaiService.updateTheLoai(id, theLoaiRequest);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/detele/{id}")
    public ResponseEntity<TheLoai> deleteTheLoai(@PathVariable Integer id) {
        theLoaiService.deleteTheLoai(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
