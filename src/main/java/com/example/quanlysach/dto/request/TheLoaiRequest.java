package com.example.quanlysach.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheLoaiRequest {
    private String maTheLoai;
    private String tenTheLoai;
}
