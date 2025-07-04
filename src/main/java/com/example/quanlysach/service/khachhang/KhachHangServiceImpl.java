package com.example.quanlysach.service.khachhang;

import com.example.quanlysach.dto.request.KhachHangRequest;
import com.example.quanlysach.dto.response.KhachHangResponse;
import com.example.quanlysach.entity.KhachHang;
import com.example.quanlysach.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Override
    public List<KhachHang> getAll() {
        return khachHangRepository.findAll();
    }

    @Override
    public KhachHangResponse createKhachHang(KhachHangRequest khachHang) {
        KhachHang kh = new KhachHang();
        kh.setMaKhachHang(khachHang.getMaKhachHang());
        kh.setTenKhachHang(khachHang.getTenKhachHang());
        kh.setDiaChi(khachHang.getDiaChi());
        kh.setEmail(khachHang.getEmail());
        kh.setSdt(khachHang.getSdt());
        KhachHang save = khachHangRepository.save(kh);
        return new KhachHangResponse(save.getId(),save.getMaKhachHang(),save.getTenKhachHang(),save.getDiaChi(),save.getEmail(),save.getSdt());
    }

    @Override
    public KhachHangResponse updateKhachHang(Integer id, KhachHangRequest khachHang) {
        KhachHang kh = khachHangRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        kh.setMaKhachHang(khachHang.getMaKhachHang());
        kh.setTenKhachHang(khachHang.getTenKhachHang());
        kh.setDiaChi(khachHang.getDiaChi());
        kh.setEmail(khachHang.getEmail());
        kh.setSdt(khachHang.getSdt());
        KhachHang update = khachHangRepository.save(kh);
        return new KhachHangResponse(update.getId(),update.getMaKhachHang(),update.getTenKhachHang(),update.getDiaChi(),update.getEmail(),update.getSdt());
    }

    @Override
    public void deleteKhachHang(Integer id) {
        KhachHang kh = khachHangRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        khachHangRepository.delete(kh);
    }
}
