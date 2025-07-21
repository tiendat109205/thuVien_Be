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
    BookService bookService;

    @GetMapping("/getAll")
    public List<BookResponse> getAllBook() {
        return bookService.getAllBook();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest) {
        BookResponse s = bookService.createBook(bookRequest);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody BookRequest bookRequest) {
        BookResponse s = bookService.updateBook(id,bookRequest);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
