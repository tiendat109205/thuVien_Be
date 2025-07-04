package com.example.quanlysach.service.theloai;

import com.example.quanlysach.dto.request.TheLoaiRequest;
import com.example.quanlysach.dto.response.TheLoaiResponse;
import com.example.quanlysach.entity.TheLoai;
import com.example.quanlysach.repository.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheLoaiServiceImpl implements TheLoaiService {
    @Autowired
    private TheLoaiRepository theLoaiRepository;

    @Override
    public List<TheLoai> getAllTheLoai(){
        return theLoaiRepository.findAll();
    }

    @Override
    public TheLoaiResponse createTheLoai(TheLoaiRequest theLoaiRequest) {
        TheLoai theLoai = new TheLoai();
        theLoai.setMaTheLoai(theLoaiRequest.getMaTheLoai());
        theLoai.setTenTheLoai(theLoaiRequest.getTenTheLoai());
        TheLoai save = theLoaiRepository.save(theLoai);
        return new TheLoaiResponse(save.getId(),save.getMaTheLoai(),save.getTenTheLoai());
    }

    @Override
    public TheLoaiResponse updateTheLoai(Integer id, TheLoaiRequest theLoaiRequest) {
        TheLoai tl = theLoaiRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        tl.setMaTheLoai(theLoaiRequest.getMaTheLoai());
        tl.setTenTheLoai(theLoaiRequest.getTenTheLoai());
        TheLoai update = theLoaiRepository.save(tl);
        return new TheLoaiResponse(update.getId(),update.getMaTheLoai(),update.getTenTheLoai());
    }

    @Override
    public void deleteTheLoai(Integer id) {
        TheLoai tl = theLoaiRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        theLoaiRepository.delete(tl);
    }
}
