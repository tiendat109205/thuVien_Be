package com.example.quanlysach.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SachRequest {
    private String maSach;
    private String tenSach;
    private String tacGia;
    private String nhaXuatBan;
    private Integer namPhatHanh;
    private Integer theLoai;
    private Boolean trangThai;
    private Integer soLuong;
}
