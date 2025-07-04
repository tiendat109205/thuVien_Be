package com.example.quanlysach.service.theloai;

import com.example.quanlysach.dto.request.TheLoaiRequest;
import com.example.quanlysach.dto.response.TheLoaiResponse;
import com.example.quanlysach.entity.TheLoai;

import java.util.List;

public interface TheLoaiService {
    List<TheLoai> getAllTheLoai();

    TheLoaiResponse createTheLoai(TheLoaiRequest theLoaiRequest);

    TheLoaiResponse updateTheLoai(Integer id, TheLoaiRequest theLoaiRequest);

    void deleteTheLoai(Integer id);
}
