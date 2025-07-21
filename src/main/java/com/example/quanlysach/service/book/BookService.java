package com.example.quanlysach.service.book;

import com.example.quanlysach.dto.request.BookRequest;
import com.example.quanlysach.dto.response.BookResponse;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBook();

    BookResponse createBook(BookRequest bookRequest);

    BookResponse updateBook(Integer id, BookRequest bookRequest);

    void deleteBook(Integer id);
}
