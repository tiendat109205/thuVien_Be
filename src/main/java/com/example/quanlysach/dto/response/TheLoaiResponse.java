package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheLoaiResponse {
    private Integer id;
    private String maTheLoai;
    private String tenTheLoai;
}
