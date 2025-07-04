package com.example.quanlysach.repository;

import com.example.quanlysach.dto.response.KhachDaMuonSachResponse;
import com.example.quanlysach.dto.response.PhieuMuonChiTietResponse;
import com.example.quanlysach.dto.response.SachDaMuonResponse;
import com.example.quanlysach.entity.PhieuMuon;
import com.example.quanlysach.entity.PhieuMuonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhieuMuonChiTietRepository extends JpaRepository<PhieuMuonChiTiet, Integer> {
    @Query("""
        SELECT new com.example.quanlysach.dto.response.PhieuMuonChiTietResponse(
            pmct.id,
            pm.id,
            s.id,
            kh.tenKhachHang,
            s.tenSach,
            pm.ngayMuon,
            pm.ngayTra,
            s.soLuong,
            pmct.soLuongSachMuon
        )
        FROM PhieuMuonChiTiet pmct
        JOIN pmct.phieuMuon pm
        JOIN pm.khachHang kh
        JOIN pmct.sach s
    """)
    List<PhieuMuonChiTietResponse> getAllChiTietPhieuMuon();

    @Query("""
        SELECT new com.example.quanlysach.dto.response.SachDaMuonResponse(
            pmct.id,s.maSach, s.tenSach, s.tacGia, s.nhaXuatBan,pm.ngayMuon,pmct.ngayHetHan,pmct.soLuongSachMuon
        )
        FROM PhieuMuonChiTiet pmct
        JOIN pmct.sach s
        JOIN pmct.phieuMuon pm
        JOIN pm.khachHang kh
        WHERE kh.id = :khachHangId
    """)
    List<SachDaMuonResponse> getSachDaMuonTheoKhachHang(@Param("khachHangId") Integer khachHangId);

    @Query("""
    SELECT new com.example.quanlysach.dto.response.KhachDaMuonSachResponse(
            kh.maKhachHang, kh.tenKhachHang, kh.email, kh.sdt, pm.ngayMuon, pm.ngayTra
        )
        FROM PhieuMuonChiTiet pmct
        JOIN pmct.phieuMuon pm
        JOIN pm.khachHang kh
        WHERE pmct.sach.id = :sachId
    """)
    List<KhachDaMuonSachResponse> findKhachMuonTheoSach(@Param("sachId") Integer sachId);
    boolean existsByPhieuMuonId(Integer phieuMuonId);

    boolean existsByPhieuMuonIdAndSachId(Integer phieuMuonId, Integer sachId);

    Optional<PhieuMuonChiTiet> findByPhieuMuonIdAndSachId(Integer phieuMuonId, Integer sachId);

}
