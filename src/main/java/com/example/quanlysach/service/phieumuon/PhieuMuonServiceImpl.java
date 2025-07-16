package com.example.quanlysach.service.phieumuon;

import com.example.quanlysach.dto.request.PhieuMuonRequest;
import com.example.quanlysach.dto.response.PhieuMuonResponse;
import com.example.quanlysach.entity.KhachHang;
import com.example.quanlysach.entity.PhieuMuon;
import com.example.quanlysach.entity.TaiKhoan;
import com.example.quanlysach.repository.KhachHangRepository;
import com.example.quanlysach.repository.PhieuMuonRepository;
import com.example.quanlysach.repository.TaiKhoanRepoSitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhieuMuonServiceImpl implements PhieuMuonService {
    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private TaiKhoanRepoSitory taiKhoanRepoSitory;

    @Override
    public List<PhieuMuonResponse> getAll() {
        return phieuMuonRepository.findAll().stream().map(PhieuMuonResponse::new).collect(Collectors.toList());
    }

    @Override
    public PhieuMuonResponse create(PhieuMuonRequest phieuMuonRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Tìm tài khoản
        TaiKhoan taiKhoan = taiKhoanRepoSitory.findByTenDangNhap(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy tài khoản"));
        System.out.println("aaaaaa"+taiKhoan);
        // Tìm khách hàng gắn với tài khoản đó
        KhachHang khachHang = khachHangRepository.findByTaiKhoan_Id(taiKhoan.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tài khoản chưa có thông tin khách hàng"));
        // Kiểm tra đã có phiếu mượn đang hoạt động chưa
        Optional<PhieuMuon> existing = phieuMuonRepository.findByKhachHangId(khachHang.getId());
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Khách hàng đã có phiếu mượn.");
        }
        // Tạo phiếu mượn mới
        PhieuMuon phieuMuon = new PhieuMuon();
        phieuMuon.setKhachHang(khachHang);
        phieuMuon.setMaPhieuMuon(generateMaPhieuMuon());
        phieuMuon.setNgayMuon(new Date());
        phieuMuon.setTrangThai(true);
        PhieuMuon save = phieuMuonRepository.save(phieuMuon);
        return new PhieuMuonResponse(save);
    }

    private String generateMaPhieuMuon() {
        int numbers = 100 + new Random().nextInt(900);
        return "PM-" + numbers;
    }
    @Override
    public PhieuMuonResponse update(Integer id, PhieuMuonRequest phieuMuonRequest) {
        KhachHang khId= khachHangRepository.findById(phieuMuonRequest.getKhachHang()).orElseThrow(()->new RuntimeException("Khong tim thay khach hang"));
        PhieuMuon pm = phieuMuonRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id"+id));
        pm.setKhachHang(khId);
        pm.setMaPhieuMuon(phieuMuonRequest.getMaPhieuMuon());
        pm.setNgayMuon(phieuMuonRequest.getNgayMuon());
        pm.setNgayTra(phieuMuonRequest.getNgayTra());
        pm.setTrangThai(phieuMuonRequest.getTrangThai());
        PhieuMuon update = phieuMuonRepository.save(pm);
        return new PhieuMuonResponse(update);
    }

    @Override
    public void delete(Integer id) {
        PhieuMuon pm = phieuMuonRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id"+id));
        phieuMuonRepository.deleteById(id);
    }
}
