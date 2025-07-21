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
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Override
    public List<BookResponse> getAllBook() {
        return bookRepository.getAll().stream().map(BookResponse::new).collect(Collectors.toList());
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        Genre genre = genreRepository.findById(bookRequest.getGenre()).orElseThrow(()-> new RuntimeException("No genre found"));
        Book book = new Book();
        book.setBookCode(bookRequest.getBookCode());
        book.setBookName(bookRequest.getBookName());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublisher(bookRequest.getPublisher());
        book.setYearToRelease(bookRequest.getYearToRelease());
        book.setGenre(genre);
        book.setStatus(bookRequest.getStatus());
        book.setQuantity(bookRequest.getQuantity());
        Book save = bookRepository.save(book);
        return new BookResponse(save.getId(),save.getBookCode(),save.getBookName(),save.getAuthor(),save.getPublisher(),save.getYearToRelease(),save.getGenre().getGenreName(),save.getStatus(),save.getQuantity());
    }

    @Override
    public BookResponse updateBook(Integer id, BookRequest bookRequest) {
        Genre genre = genreRepository.findById(bookRequest.getGenre()).orElseThrow(()-> new RuntimeException("No genre found"));
        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot find book id"+id));
        book.setBookCode(bookRequest.getBookCode());
        book.setBookName(bookRequest.getBookName());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublisher(bookRequest.getPublisher());
        book.setYearToRelease(bookRequest.getYearToRelease());
        book.setGenre(genre);
        book.setStatus(bookRequest.getStatus());
        book.setQuantity(bookRequest.getQuantity());
        Book update = bookRepository.save(book);
        return new BookResponse(update);
    }

    @Override
    public void deleteBook(Integer id) {
        Book s = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Can't find id book"+id));
        bookRepository.delete(s);
    }
}
