package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.GenreRequest;
import com.example.quanlysach.dto.response.GenreResponse;
import com.example.quanlysach.entity.Genre;
import com.example.quanlysach.service.genre.GenreService;
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
public class GenreController {
    @Autowired
    GenreService genreService;

    @GetMapping("/getAll")
    public List<Genre> getAllGenre() {
        return genreService.getAllGenre();
    }
    @PostMapping("/add")
    public ResponseEntity<?> addGenre(@RequestBody GenreRequest genreRequest) {
        GenreResponse result = genreService.createGenre(genreRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGenre(@RequestBody GenreRequest genreRequest, @PathVariable Integer id) {
        GenreResponse result = genreService.updateGenre(id, genreRequest);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/detele/{id}")
    public ResponseEntity<Genre> deleteGenre(@PathVariable Integer id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
