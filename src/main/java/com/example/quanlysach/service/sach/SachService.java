package com.example.quanlysach.service.sach;

import com.example.quanlysach.dto.request.SachRequest;
import com.example.quanlysach.dto.response.SachResponse;
import com.example.quanlysach.entity.Sach;

import java.util.List;

public interface SachService {
    List<SachResponse> getAllSach();

    SachResponse createSach(SachRequest sach);

    SachResponse updateSach(Integer id, SachRequest sach);

    void deleteSach(Integer id);
}
