package com.example.quanlysach.service.account;

import com.example.quanlysach.entity.Account;
import com.example.quanlysach.repository.AccountRepoSitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepoSitory taiKhoanRepoSitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = taiKhoanRepoSitory.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Đã được mã hóa (BCrypt)
                .roles(user.getRole().replace("ROLE_", "")) // "ROLE_USER" → "USER"
                .build();
    }

}
