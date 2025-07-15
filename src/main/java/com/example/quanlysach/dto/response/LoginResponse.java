package com.example.quanlysach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tenDangNhap;
    private String vaiTro;
    private Integer id;
}
