package com.example.quanlysach.service.genre;

import com.example.quanlysach.dto.request.GenreRequest;
import com.example.quanlysach.dto.response.GenreResponse;
import com.example.quanlysach.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllTheLoai();

    GenreResponse createTheLoai(GenreRequest theLoaiRequest);

    GenreResponse updateTheLoai(Integer id, GenreRequest theLoaiRequest);

    void deleteTheLoai(Integer id);
}
