package com.example.quanlysach.service.phieumuonchitiet;

import com.example.quanlysach.dto.request.PhieuMuonChiTietRequest;
import com.example.quanlysach.dto.request.SachMuonRequest;
import com.example.quanlysach.dto.response.KhachDaMuonSachResponse;
import com.example.quanlysach.dto.response.PhieuMuonChiTietResponse;
import com.example.quanlysach.dto.response.SachDaMuonResponse;
import com.example.quanlysach.entity.PhieuMuon;
import com.example.quanlysach.entity.PhieuMuonChiTiet;
import com.example.quanlysach.entity.Sach;
import com.example.quanlysach.repository.PhieuMuonChiTietRepository;
import com.example.quanlysach.repository.PhieuMuonRepository;
import com.example.quanlysach.repository.SachRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuMuonChiTietServiceImpl implements PhieuMuonChiTietService {
    @Autowired
    private PhieuMuonChiTietRepository phieuMuonChiTietRepository;
    @Autowired
    private PhieuMuonRepository phieuMuonRepository;
    @Autowired
    private SachRepository sachRepository;

    @Override
    public List<PhieuMuonChiTietResponse> getAll() {
        List<PhieuMuonChiTiet> chiTietList = phieuMuonChiTietRepository.findAll();
        List<PhieuMuonChiTietResponse> responseList = new ArrayList<>();

        for (PhieuMuonChiTiet ct : chiTietList) {
            PhieuMuonChiTietResponse res = new PhieuMuonChiTietResponse();
            res.setId(ct.getId());
            res.setPhieuMuon(ct.getPhieuMuon().getId());
            res.setSach(ct.getSach().getId());
            res.setTenKhachHang(ct.getPhieuMuon().getKhachHang().getTenKhachHang());
            res.setTenSach(ct.getSach().getTenSach());
            res.setNgayMuon(ct.getPhieuMuon().getNgayMuon());
            res.setNgayHetHan(ct.getNgayHetHan());

            responseList.add(res);
        }

        return responseList;
    }

    @Override
    public List<SachDaMuonResponse> getSachDaMuonTheoKH(Integer khachHangId) {
        return phieuMuonChiTietRepository.getSachDaMuonTheoKhachHang(khachHangId);
    }

    @Override
    public List<KhachDaMuonSachResponse> getKhachDaMuonSach(Integer sachId) {
        return phieuMuonChiTietRepository.findKhachMuonTheoSach(sachId);
    }

    @Override
    @Transactional
    public List<PhieuMuonChiTietResponse> create(PhieuMuonChiTietRequest request) {
        PhieuMuon phieuMuon = layPhieuMuon(request.getPhieuMuonId());
        phieuMuon.setNgayMuon(new Date());
        List<PhieuMuonChiTietResponse> responses = new ArrayList<>();

        for (SachMuonRequest item : request.getSachChiTiet()) {
            Sach sach = xuLySachMuon(item); // Lấy và trừ số lượng
            PhieuMuonChiTiet chiTiet = taoHoacCapNhatChiTiet(phieuMuon, sach, item.getSoLuong(), request.getNgayHetHan());
            PhieuMuonChiTiet saved = phieuMuonChiTietRepository.save(chiTiet);

            PhieuMuonChiTietResponse response = new PhieuMuonChiTietResponse(chiTiet.getId(),
                    phieuMuon.getId(),
                    sach.getId(),
                    phieuMuon.getKhachHang().getTenKhachHang(),
                    sach.getTenSach(),
                    phieuMuon.getNgayMuon(),
                    chiTiet.getNgayHetHan(),
                    sach.getSoLuong(),
                    chiTiet.getSoLuongSachMuon());
            responses.add(response);
        }

        phieuMuon.setTrangThai(false);
        phieuMuonRepository.save(phieuMuon);

        return responses;
    }


    private PhieuMuon layPhieuMuon(Integer phieuMuonId) {
        return phieuMuonRepository.findById(phieuMuonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn"));
    }

    private Sach xuLySachMuon(SachMuonRequest item) {
        Sach sach = sachRepository.findById(item.getSachId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách ID: " + item.getSachId()));

        if (sach.getSoLuong() < item.getSoLuong()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không đủ số lượng sách: " + sach.getTenSach());
        }

        sach.setSoLuong(sach.getSoLuong() - item.getSoLuong());
        return sachRepository.save(sach);
    }

    private PhieuMuonChiTiet taoHoacCapNhatChiTiet(PhieuMuon phieuMuon, Sach sach, int soLuong, Date ngayHetHan) {
        Optional<PhieuMuonChiTiet> optionalChiTiet =
                phieuMuonChiTietRepository.findByPhieuMuonIdAndSachId(phieuMuon.getId(), sach.getId());

        if (optionalChiTiet.isPresent()) {
            PhieuMuonChiTiet chiTiet = optionalChiTiet.get();
            chiTiet.setSoLuongSachMuon(chiTiet.getSoLuongSachMuon() + soLuong);
            chiTiet.setNgayHetHan(ngayHetHan);
            return chiTiet;
        } else {
            PhieuMuonChiTiet chiTiet = new PhieuMuonChiTiet();
            chiTiet.setPhieuMuon(phieuMuon);
            chiTiet.setSach(sach);
            chiTiet.setNgayHetHan(ngayHetHan);
            chiTiet.setSoLuongSachMuon(soLuong);
            return chiTiet;
        }
    }


    @Transactional
    @Override
    public void traSach(Integer chiTietId, Integer soLuongTra) {
        // 1. Tìm chi tiết phiếu mượn
        PhieuMuonChiTiet chiTiet = phieuMuonChiTietRepository.findById(chiTietId)
                .orElseThrow(() -> new RuntimeException("Chi tiết phiếu mượn ID " + chiTietId + " không tồn tại"));
        // 2. Trả sách về kho
        capNhatSoLuongSachSauKhiTra(chiTiet.getSach(), soLuongTra);

        xuLyChiTietPhieuMuonSauKhiTra(chiTiet, soLuongTra);

        Integer phieuMuonId = chiTiet.getPhieuMuon().getId();
        // 5. Kiểm tra xem phiếu mượn còn sách nào không
        boolean conSach = phieuMuonChiTietRepository.existsByPhieuMuonId(phieuMuonId);
        if (!conSach) {
            // 6. Nếu không còn sách → cập nhật trạng thái đã trả hết
            PhieuMuon phieuMuon = phieuMuonRepository.findById(phieuMuonId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn ID: " + phieuMuonId));
            phieuMuon.setTrangThai(true);
            phieuMuon.setNgayTra(new Date());
            phieuMuonRepository.save(phieuMuon);
        }
    }

    private void capNhatSoLuongSachSauKhiTra(Sach sach, Integer soLuongTra) {
        sach.setSoLuong(sach.getSoLuong() + soLuongTra);
        sachRepository.save(sach);
    }

    private void xuLyChiTietPhieuMuonSauKhiTra(PhieuMuonChiTiet chiTiet, Integer soLuongTra) {
        if (soLuongTra < chiTiet.getSoLuongSachMuon()) {
            chiTiet.setSoLuongSachMuon(chiTiet.getSoLuongSachMuon() - soLuongTra);
            phieuMuonChiTietRepository.save(chiTiet);
        } else {
            phieuMuonChiTietRepository.deleteById(chiTiet.getId());
        }
    }

}
