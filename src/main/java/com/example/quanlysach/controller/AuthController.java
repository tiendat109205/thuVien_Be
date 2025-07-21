package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.LoginRequest;
import com.example.quanlysach.dto.request.RegisterRequest;
import com.example.quanlysach.dto.response.LoginResponse;
import com.example.quanlysach.entity.Account;
import com.example.quanlysach.jwt.JwtProvider;
import com.example.quanlysach.repository.AccountRepoSitory;
import com.example.quanlysach.service.account.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;
    private final AccountRepoSitory taiKhoanRepoSitory;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Đăng nhập với: " + loginRequest.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword())
            );
            System.out.println("Xác thực thành công");
        } catch (AuthenticationException e) {
            System.out.println("Xác thực thất bại: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sai tài khoản hoặc mật khẩu");
        }

        // Lấy chi tiết user
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtProvider.generateToken(userDetails);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        // ✅ Lấy ID tài khoản từ database
        Account taiKhoan = taiKhoanRepoSitory.findByUsername(userDetails.getUsername()).orElse(null);
        Integer id = (taiKhoan != null) ? taiKhoan.getId() : null;

        System.out.println("Token: " + token);

        // ✅ Trả về cả ID
        return ResponseEntity.ok(new LoginResponse(token, userDetails.getUsername(), role, id));
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if(taiKhoanRepoSitory.findByUsername(registerRequest.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ten dang nhap da ton tai");
        }
        Account tk = new Account();
        tk.setUsername(registerRequest.getUsername());
        tk.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPassword()));
        tk.setRole("ROLE_USER");
        taiKhoanRepoSitory.save(tk);
        return ResponseEntity.ok("Dang ky thanh cong");
    }
}
