package com.example.quanlysach.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String bookCode;
    private String bookName;
    private String author;
    private String publisher;
    private Integer yearToRelease;
    private Integer genre;
    private Boolean status;
    private Integer quantity;
}
