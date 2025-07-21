package com.example.quanlysach.repository;

import com.example.quanlysach.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepoSitory extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
