package com.example.quanlysach.repository;

import com.example.quanlysach.entity.LoanVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanVoucherRepository extends JpaRepository<LoanVoucher, Integer> {
    Optional<LoanVoucher> findByCustomerId(Integer khachHangId);
}
