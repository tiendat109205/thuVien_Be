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
    private GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenre(){
        return genreRepository.findAll();
    }

    @Override
    public GenreResponse createGenre(GenreRequest genreRequest) {
        Genre genre = new Genre();
        genre.setGenreCode(genreRequest.getGenreCode());
        genre.setGenreName(genreRequest.getGenreName());
        Genre save = genreRepository.save(genre);
        return new GenreResponse(save.getId(),save.getGenreCode(),save.getGenreName());
    }

    @Override
    public GenreResponse updateGenre(Integer id, GenreRequest genreRequest) {
        Genre genre = genreRepository.findById(id).orElseThrow(()->new RuntimeException("No id found:"+id));
        genre.setGenreCode(genreRequest.getGenreCode());
        genre.setGenreName(genreRequest.getGenreName());
        Genre update = genreRepository.save(genre);
        return new GenreResponse(update.getId(),update.getGenreCode(),update.getGenreName());
    }

    @Override
    public void deleteGenre(Integer id) {
        Genre tl = genreRepository.findById(id).orElseThrow(()->new RuntimeException("No id found:"+id));
        genreRepository.delete(tl);
    }
}
