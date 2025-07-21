package com.example.quanlysach.service.genre;

import com.example.quanlysach.dto.request.GenreRequest;
import com.example.quanlysach.dto.response.GenreResponse;
import com.example.quanlysach.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenre();

    GenreResponse createGenre(GenreRequest genreRequest);

    GenreResponse updateGenre(Integer id, GenreRequest genreRequest);

    void deleteGenre(Integer id);
}
