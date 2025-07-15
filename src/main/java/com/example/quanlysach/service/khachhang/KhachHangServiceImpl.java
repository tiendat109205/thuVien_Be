package com.example.quanlysach.service.khachhang;

import com.example.quanlysach.dto.request.KhachHangRequest;
import com.example.quanlysach.dto.response.KhachHangResponse;
import com.example.quanlysach.entity.KhachHang;
import com.example.quanlysach.entity.TaiKhoan;
import com.example.quanlysach.repository.KhachHangRepository;
import com.example.quanlysach.repository.TaiKhoanRepoSitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private TaiKhoanRepoSitory taiKhoanRepoSitory;

    public List<KhachHangResponse> getAll() {
        List<KhachHang> danhSach = khachHangRepository.findAll();

        return danhSach.stream().map(kh -> new KhachHangResponse(
                kh.getId(),
                kh.getMaKhachHang(),
                kh.getTenKhachHang(),
                kh.getDiaChi(),
                kh.getEmail(),
                kh.getSdt(),
                kh.getTaiKhoan() != null ? kh.getTaiKhoan().getId() : null
        )).collect(Collectors.toList());
    }



    @Override
    public KhachHangResponse createKhachHang(KhachHangRequest khachHang) {
        // Lấy người dùng đang đăng nhập
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Tìm tài khoản từ username
        TaiKhoan taiKhoan = taiKhoanRepoSitory.findByTenDangNhap(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        KhachHang kh = new KhachHang();
        kh.setMaKhachHang(khachHang.getMaKhachHang());
        kh.setTenKhachHang(khachHang.getTenKhachHang());
        kh.setDiaChi(khachHang.getDiaChi());
        kh.setEmail(khachHang.getEmail());
        kh.setSdt(khachHang.getSdt());
        kh.setTaiKhoan(taiKhoan);
        KhachHang save = khachHangRepository.save(kh);
        return new KhachHangResponse(
                save.getId(),
                save.getMaKhachHang(),
                save.getTenKhachHang(),
                save.getDiaChi(),
                save.getEmail(),
                save.getSdt(),
                save.getTaiKhoan().getId()
        );
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
        return new KhachHangResponse(update.getId(),update.getMaKhachHang(),update.getTenKhachHang(),update.getDiaChi(),update.getEmail(),update.getSdt(),update.getTaiKhoan().getId());
    }

    @Override
    public void deleteKhachHang(Integer id) {
        KhachHang kh = khachHangRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        khachHangRepository.delete(kh);
    }

    //check tt khachhang với id vừa đăng nhập
    @Override
    public boolean daCoThongTinKhachHang(Integer taiKhoanId) {
        return khachHangRepository.existsByTaiKhoan_Id(taiKhoanId);
    }
}
