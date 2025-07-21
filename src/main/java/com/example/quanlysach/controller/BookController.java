package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.BookRequest;
import com.example.quanlysach.dto.response.BookResponse;
import com.example.quanlysach.service.book.BookService;
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
public class BookController {
    @Autowired
    BookService sachService;

    @GetMapping("/getAll")
    public List<BookResponse> getAllSach() {
        return sachService.getAllSach();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSach(@RequestBody BookRequest sach) {
        BookResponse s = sachService.createSach(sach);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSach(@PathVariable Integer id, @RequestBody BookRequest sach) {
        BookResponse s = sachService.updateSach(id,sach);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSach(@PathVariable Integer id) {
        sachService.deleteSach(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
