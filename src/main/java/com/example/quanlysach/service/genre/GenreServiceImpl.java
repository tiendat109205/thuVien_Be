package com.example.quanlysach.service.genre;

import com.example.quanlysach.dto.request.GenreRequest;
import com.example.quanlysach.dto.response.GenreResponse;
import com.example.quanlysach.entity.Genre;
import com.example.quanlysach.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository theLoaiRepository;

    @Override
    public List<Genre> getAllTheLoai(){
        return theLoaiRepository.findAll();
    }

    @Override
    public GenreResponse createTheLoai(GenreRequest theLoaiRequest) {
        Genre theLoai = new Genre();
        theLoai.setGenreCode(theLoaiRequest.getGenreCode());
        theLoai.setGenreName(theLoaiRequest.getGenreName());
        Genre save = theLoaiRepository.save(theLoai);
        return new GenreResponse(save.getId(),save.getGenreCode(),save.getGenreName());
    }

    @Override
    public GenreResponse updateTheLoai(Integer id, GenreRequest theLoaiRequest) {
        Genre tl = theLoaiRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        tl.setGenreCode(theLoaiRequest.getGenreCode());
        tl.setGenreName(theLoaiRequest.getGenreName());
        Genre update = theLoaiRepository.save(tl);
        return new GenreResponse(update.getId(),update.getGenreCode(),update.getGenreName());
    }

    @Override
    public void deleteTheLoai(Integer id) {
        Genre tl = theLoaiRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        theLoaiRepository.delete(tl);
    }
}
