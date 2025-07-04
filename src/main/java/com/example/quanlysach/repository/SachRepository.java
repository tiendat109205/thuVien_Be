package com.example.quanlysach.repository;

import com.example.quanlysach.dto.response.SachResponse;
import com.example.quanlysach.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SachRepository extends JpaRepository<Sach, Integer> {
    @Query(value = "select * from sach ", nativeQuery = true)
    List<Sach> getAll();
}
