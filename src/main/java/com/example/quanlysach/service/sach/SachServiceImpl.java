package com.example.quanlysach.service.sach;

import com.example.quanlysach.dto.request.SachRequest;
import com.example.quanlysach.dto.response.SachResponse;
import com.example.quanlysach.entity.Sach;
import com.example.quanlysach.entity.TheLoai;
import com.example.quanlysach.repository.SachRepository;
import com.example.quanlysach.repository.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SachServiceImpl implements SachService {
    @Autowired
    SachRepository sachRepository;

    @Autowired
    TheLoaiRepository theLoaiRepository;

    @Override
    public List<SachResponse> getAllSach() {
        return sachRepository.getAll().stream().map(SachResponse::new).collect(Collectors.toList());
    }

    @Override
    public SachResponse createSach(SachRequest sach) {
        TheLoai theLoai = theLoaiRepository.findById(sach.getTheLoai()).orElseThrow(()-> new RuntimeException("Khong tim thay the loai"));
        Sach s = new Sach();
        s.setMaSach(sach.getMaSach());
        s.setTenSach(sach.getTenSach());
        s.setTacGia(sach.getTacGia());
        s.setNhaXuatBan(sach.getNhaXuatBan());
        s.setNamPhatHanh(sach.getNamPhatHanh());
        s.setTheLoai(theLoai);
        s.setTrangThai(sach.getTrangThai());
        s.setSoLuong(sach.getSoLuong());
        Sach save = sachRepository.save(s);
        return new SachResponse(save.getId(),save.getMaSach(),save.getTenSach(),save.getTacGia(),save.getNhaXuatBan(),save.getNamPhatHanh(),save.getTheLoai().getTenTheLoai(),save.getTrangThai(),save.getSoLuong());
    }

    @Override
    public SachResponse updateSach(Integer id, SachRequest sach) {
        TheLoai theLoai = theLoaiRepository.findById(sach.getTheLoai()).orElseThrow(()-> new RuntimeException("Khong tim thay the loai"));
        Sach s = sachRepository.findById(id).orElseThrow(()-> new RuntimeException("Khong tim id sach"+id));
        s.setMaSach(sach.getMaSach());
        s.setTenSach(sach.getTenSach());
        s.setTacGia(sach.getTacGia());
        s.setNhaXuatBan(sach.getNhaXuatBan());
        s.setNamPhatHanh(sach.getNamPhatHanh());
        s.setTheLoai(theLoai);
        s.setTrangThai(sach.getTrangThai());
        s.setSoLuong(sach.getSoLuong());
        Sach update = sachRepository.save(s);
        return new SachResponse(update);
    }

    @Override
    public void deleteSach(Integer id) {
        Sach s = sachRepository.findById(id).orElseThrow(()-> new RuntimeException("Khong tim thay id sach"+id));
        sachRepository.delete(s);
    }
}
