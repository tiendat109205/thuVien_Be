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
    @Transactional
    public List<PhieuMuonChiTietResponse> create(PhieuMuonChiTietRequest request) {
        // 1. Lấy thông tin phiếu mượn từ ID, nếu không tìm thấy thì ném lỗi
        PhieuMuon phieuMuon = phieuMuonRepository.findById(request.getPhieuMuonId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn"));
        // 2. Tạo danh sách response để trả về client
        List<PhieuMuonChiTietResponse> responses = new ArrayList<>();
        // 3. Duyệt qua từng sách được gửi lên trong request
        for (SachMuonRequest item : request.getSachChiTiet()) {
            Integer sachId = item.getSachId();        // ID của sách cần mượn
            Integer soLuong = item.getSoLuong();      // Số lượng cần mượn
            // 4. Lấy thông tin sách từ database
            Sach sach = sachRepository.findById(sachId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sách ID: " + sachId));
            // 5. Kiểm tra xem sách còn đủ số lượng hay không
            if (sach.getSoLuong() < soLuong) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không đủ số lượng sách: " + sach.getTenSach());
            }
            // 6. Trừ số lượng sách trong kho (vì đã mượn)
            sach.setSoLuong(sach.getSoLuong() - soLuong);
            sachRepository.save(sach);  // Lưu lại số lượng mới của sách
            // 7. Kiểm tra xem sách này đã được mượn trong phiếu mượn chưa (đã có chi tiết chưa)
            Optional<PhieuMuonChiTiet> optionalChiTiet = phieuMuonChiTietRepository.findByPhieuMuonIdAndSachId(phieuMuon.getId(), sachId);

            PhieuMuonChiTiet chiTiet;
            if (optionalChiTiet.isPresent()) {
                // 8. Nếu sách đã được mượn trước đó (trong cùng phiếu mượn), cộng dồn số lượng
                chiTiet = optionalChiTiet.get();
                chiTiet.setSoLuongSachMuon(chiTiet.getSoLuongSachMuon() + soLuong);
                // 9. Cập nhật lại ngày hết hạn (nếu muốn, hoặc có thể bỏ dòng này)
                chiTiet.setNgayHetHan(request.getNgayHetHan());
            } else {
                // 10. Nếu sách chưa từng mượn trong phiếu mượn, tạo mới chi tiết mượn
                chiTiet = new PhieuMuonChiTiet();
                chiTiet.setPhieuMuon(phieuMuon);                  // Gán phiếu mượn
                chiTiet.setSach(sach);                            // Gán sách
                chiTiet.setNgayHetHan(request.getNgayHetHan());   // Gán ngày hết hạn
                chiTiet.setSoLuongSachMuon(soLuong);              // Gán số lượng sách mượn
            }
            // 11. Lưu chi tiết mượn vào database
            PhieuMuonChiTiet saved = phieuMuonChiTietRepository.save(chiTiet);
            // 12. Tạo response trả về phía frontend
            PhieuMuonChiTietResponse response = new PhieuMuonChiTietResponse(
                    saved.getId(),
                    phieuMuon.getId(),
                    sach.getId(),
                    phieuMuon.getKhachHang().getTenKhachHang(),
                    sach.getTenSach(),
                    phieuMuon.getNgayMuon(),
                    saved.getNgayHetHan(),
                    sach.getSoLuong(),          // Số lượng còn lại sau khi trừ
                    saved.getSoLuongSachMuon()  // Tổng số lượng đã mượn
            );
            // 13. Thêm vào danh sách kết quả
            responses.add(response);
        }
        // 14. Sau khi mượn xong, cập nhật trạng thái phiếu mượn (đang mượn = false)
        phieuMuon.setTrangThai(false);
        phieuMuonRepository.save(phieuMuon);
        // 15. Trả về danh sách chi tiết đã xử lý
        return responses;
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
    public void delete(Integer id) {

    }

    @Transactional
    @Override
    public void traSach(Integer chiTietId, Integer soLuongTra) {
        // 1. Tìm chi tiết phiếu mượn
        PhieuMuonChiTiet chiTiet = phieuMuonChiTietRepository.findById(chiTietId)
                .orElseThrow(() -> new RuntimeException("Chi tiết phiếu mượn ID " + chiTietId + " không tồn tại"));
        Integer soLuongDangMuon = chiTiet.getSoLuongSachMuon();
        if (soLuongTra <= 0) {
            throw new IllegalArgumentException("Số lượng trả phải lớn hơn 0");
        }
        if (soLuongTra > soLuongDangMuon) {
            throw new IllegalArgumentException("Số lượng trả vượt quá số lượng đã mượn");
        }
        // 2. Trả sách về kho
        Sach sach = chiTiet.getSach();
        sach.setSoLuong(sach.getSoLuong() + soLuongTra);
        sachRepository.save(sach);
        Integer phieuMuonId = chiTiet.getPhieuMuon().getId();
        if (soLuongTra < soLuongDangMuon) {
            // 3. Nếu trả một phần → giảm số lượng mượn
            chiTiet.setSoLuongSachMuon(soLuongDangMuon - soLuongTra);
            phieuMuonChiTietRepository.save(chiTiet);
        } else {
            // 4. Nếu trả hết → xóa chi tiết phiếu mượn
            phieuMuonChiTietRepository.deleteById(chiTietId);
        }
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

}
