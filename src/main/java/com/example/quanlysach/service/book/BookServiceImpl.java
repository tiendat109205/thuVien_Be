package com.example.quanlysach.service.book;

import com.example.quanlysach.dto.request.BookRequest;
import com.example.quanlysach.dto.response.BookResponse;
import com.example.quanlysach.entity.Book;
import com.example.quanlysach.entity.Genre;
import com.example.quanlysach.repository.BookRepository;
import com.example.quanlysach.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository sachRepository;

    @Autowired
    GenreRepository theLoaiRepository;

    @Override
    public List<BookResponse> getAllSach() {
        return sachRepository.getAll().stream().map(BookResponse::new).collect(Collectors.toList());
    }

    @Override
    public BookResponse createSach(BookRequest sach) {
        Genre theLoai = theLoaiRepository.findById(sach.getGenre()).orElseThrow(()-> new RuntimeException("Khong tim thay the loai"));
        Book s = new Book();
        s.setBookCode(sach.getBookCode());
        s.setBookName(sach.getBookName());
        s.setAuthor(sach.getAuthor());
        s.setPublisher(sach.getPublisher());
        s.setYearToRelease(sach.getYearToRelease());
        s.setGenre(theLoai);
        s.setStatus(sach.getStatus());
        s.setQuantity(sach.getQuantity());
        Book save = sachRepository.save(s);
        return new BookResponse(save.getId(),save.getBookCode(),save.getBookName(),save.getAuthor(),save.getPublisher(),save.getYearToRelease(),save.getGenre().getGenreName(),save.getStatus(),save.getQuantity());
    }

    @Override
    public BookResponse updateSach(Integer id, BookRequest sach) {
        Genre theLoai = theLoaiRepository.findById(sach.getGenre()).orElseThrow(()-> new RuntimeException("Khong tim thay the loai"));
        Book s = sachRepository.findById(id).orElseThrow(()-> new RuntimeException("Khong tim id sach"+id));
        s.setBookCode(sach.getBookCode());
        s.setBookName(sach.getBookName());
        s.setAuthor(sach.getAuthor());
        s.setPublisher(sach.getPublisher());
        s.setYearToRelease(sach.getYearToRelease());
        s.setGenre(theLoai);
        s.setStatus(sach.getStatus());
        s.setQuantity(sach.getQuantity());
        Book update = sachRepository.save(s);
        return new BookResponse(update);
    }

    @Override
    public void deleteSach(Integer id) {
        Book s = sachRepository.findById(id).orElseThrow(()-> new RuntimeException("Khong tim thay id sach"+id));
        sachRepository.delete(s);
    }
}
