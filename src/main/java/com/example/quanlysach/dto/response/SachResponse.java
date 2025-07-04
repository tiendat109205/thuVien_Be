package com.example.quanlysach.dto.response;

import com.example.quanlysach.entity.Sach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SachResponse {
    private Integer id;
    private String maSach;
    private String tenSach;
    private String tacGia;
    private String nhaXuatBan;
    private Integer namPhatHanh;
    private String theLoai;
    private Boolean trangThai;
    private Integer soLuong;

    public SachResponse(Sach sach) {
        this.id = sach.getId();
        this.maSach = sach.getMaSach();
        this.tenSach = sach.getTenSach();
        this.tacGia = sach.getTacGia();
        this.nhaXuatBan = sach.getNhaXuatBan();
        this.namPhatHanh = sach.getNamPhatHanh();
        this.theLoai = sach.getTheLoai().getTenTheLoai();
        this.trangThai = sach.getTrangThai();
        this.soLuong = sach.getSoLuong();
    }
}
