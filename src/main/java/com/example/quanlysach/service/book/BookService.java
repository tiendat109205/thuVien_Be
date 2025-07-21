package com.example.quanlysach.service.book;

import com.example.quanlysach.dto.request.BookRequest;
import com.example.quanlysach.dto.response.BookResponse;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllSach();

    BookResponse createSach(BookRequest sach);

    BookResponse updateSach(Integer id, BookRequest sach);

    void deleteSach(Integer id);
}
