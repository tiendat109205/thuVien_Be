package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowResponse {
    private Integer id;
    private String bookCode;
    private String bookName;
    private String author;
    private String publisher;
    private Date borrowDate;
    private Date expiryDate;
    private Integer numberBookBorrow;
}
