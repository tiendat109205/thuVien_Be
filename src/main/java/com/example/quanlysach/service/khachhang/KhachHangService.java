package com.example.quanlysach.service.khachhang;

import com.example.quanlysach.dto.request.KhachHangRequest;
import com.example.quanlysach.dto.response.KhachHangResponse;
import com.example.quanlysach.entity.KhachHang;

import java.util.List;

public interface KhachHangService {
    List<KhachHangResponse> getAll();
    KhachHangResponse createKhachHang(KhachHangRequest khachHang);
    KhachHangResponse updateKhachHang(Integer id, KhachHangRequest khachHang);
    void deleteKhachHang(Integer id);

    boolean daCoThongTinKhachHang(Integer taiKhoanId);
}
