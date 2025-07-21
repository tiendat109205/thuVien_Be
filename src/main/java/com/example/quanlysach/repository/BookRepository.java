package com.example.quanlysach.repository;

import com.example.quanlysach.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select * from sach ", nativeQuery = true)
    List<Book> getAll();
}
