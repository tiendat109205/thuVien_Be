package com.example.quanlysach.repository;

import com.example.quanlysach.entity.PhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, Integer> {
    Optional<PhieuMuon> findByKhachHangId(Integer khachHangId);
}
