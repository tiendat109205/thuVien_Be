package com.example.quanlysach.service.tai_khoan;

import com.example.quanlysach.entity.TaiKhoan;
import com.example.quanlysach.repository.TaiKhoanRepoSitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final TaiKhoanRepoSitory taiKhoanRepoSitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TaiKhoan user = taiKhoanRepoSitory.findByTenDangNhap(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản"));

        return User.builder()
                .username(user.getTenDangNhap())
                .password(user.getMatKhau()) // Đã được mã hóa (BCrypt)
                .roles(user.getVaiTro().replace("ROLE_", "")) // "ROLE_USER" → "USER"
                .build();
    }

}
