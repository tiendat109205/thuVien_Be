package com.example.quanlysach.repository;

import com.example.quanlysach.entity.Customer;
import com.example.quanlysach.entity.LoanVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "select *from phieu_muon",nativeQuery = true)
    List<LoanVoucher> getAll();

    boolean existsByAccount_Id(Integer taiKhoanId);
    Optional<Customer> findByAccount_Id(Integer taiKhoanId);

}
