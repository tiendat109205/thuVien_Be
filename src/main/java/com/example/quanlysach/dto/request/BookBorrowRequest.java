package com.example.quanlysach.dto.request;

import lombok.Data;

@Data
public class BookBorrowRequest {
    private Integer bookId;
    private Integer quantityBorrow;
}
