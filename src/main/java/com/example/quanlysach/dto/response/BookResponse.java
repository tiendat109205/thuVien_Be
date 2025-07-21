package com.example.quanlysach.dto.response;

import com.example.quanlysach.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Integer id;
    private String bookCode;
    private String bookName;
    private String author;
    private String publisher;
    private Integer yearToRelease;
    private String genre;
    private Boolean status;
    private Integer quantity;

    public BookResponse(Book sach) {
        this.id = sach.getId();
        this.bookCode = sach.getBookCode();
        this.bookName = sach.getBookName();
        this.author = sach.getAuthor();
        this.publisher = sach.getPublisher();
        this.yearToRelease = sach.getYearToRelease();
        this.genre = sach.getGenre().getGenreName();
        this.status = sach.getStatus();
        this.quantity = sach.getQuantity();
    }
}
