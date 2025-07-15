package com.example.quanlysach.repository;

import com.example.quanlysach.entity.KhachHang;
import com.example.quanlysach.entity.PhieuMuon;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    @Query(value = "select *from phieu_muon",nativeQuery = true)
    List<PhieuMuon> getAll();

    boolean existsByTaiKhoan_Id(Integer taiKhoanId);
    Optional<KhachHang> findByTaiKhoan_Id(Integer taiKhoanId);

}
