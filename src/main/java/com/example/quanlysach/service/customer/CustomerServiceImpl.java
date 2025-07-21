package com.example.quanlysach.service.customer;

import com.example.quanlysach.dto.request.CustomerRequest;
import com.example.quanlysach.dto.response.CustomerResponse;
import com.example.quanlysach.entity.Customer;
import com.example.quanlysach.entity.Account;
import com.example.quanlysach.repository.CustomerRepository;
import com.example.quanlysach.repository.AccountRepoSitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository khachHangRepository;
    @Autowired
    private AccountRepoSitory taiKhoanRepoSitory;

    public List<CustomerResponse> getAll() {
        List<Customer> danhSach = khachHangRepository.findAll();

        return danhSach.stream().map(kh -> new CustomerResponse(
                kh.getId(),
                kh.getCustomerCode(),
                kh.getCustomerName(),
                kh.getAddress(),
                kh.getEmail(),
                kh.getPhoneNumber(),
                kh.getAccount() != null ? kh.getAccount().getId() : null
        )).collect(Collectors.toList());
    }



    @Override
    public CustomerResponse createKhachHang(CustomerRequest khachHang) {
        // Lấy người dùng đang đăng nhập
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Tìm tài khoản từ username
        Account taiKhoan = taiKhoanRepoSitory.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        Customer kh = new Customer();
        kh.setCustomerCode(khachHang.getCustomerCode());
        kh.setCustomerName(khachHang.getCustomerName());
        kh.setAddress(khachHang.getAddress());
        kh.setEmail(khachHang.getEmail());
        kh.setPhoneNumber(khachHang.getPhoneNumber());
        kh.setAccount(taiKhoan);
        Customer save = khachHangRepository.save(kh);
        return new CustomerResponse(
                save.getId(),
                save.getCustomerCode(),
                save.getCustomerName(),
                save.getAddress(),
                save.getEmail(),
                save.getPhoneNumber(),
                save.getAccount().getId()
        );
    }

    @Override
    public CustomerResponse updateKhachHang(Integer id, CustomerRequest khachHang) {
        //Lấy người dùng hiện tại từ token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account taiKhoan = taiKhoanRepoSitory.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        //Lấy khách hàng cần cập nhật
        Customer kh = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với ID: " + id));
        //Kiểm tra quyền truy cập
        boolean isAdmin = taiKhoan.getRole().equals("ROLE_ADMIN");
        if (!isAdmin && !kh.getAccount().getId().equals(taiKhoan.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bạn không có quyền cập nhật thông tin khách hàng này.");
        }
        //Cập nhật thông tin
        kh.setCustomerCode(khachHang.getCustomerCode());
        kh.setCustomerName(khachHang.getCustomerName());
        kh.setAddress(khachHang.getAddress());
        kh.setEmail(khachHang.getEmail());
        kh.setPhoneNumber(khachHang.getPhoneNumber());
        Customer update = khachHangRepository.save(kh);
        return new CustomerResponse(
                update.getId(),
                update.getCustomerCode(),
                update.getCustomerName(),
                update.getAddress(),
                update.getEmail(),
                update.getPhoneNumber(),
                update.getAccount().getId()
        );
    }

    @Override
    public void deleteKhachHang(Integer id) {
        Customer kh = khachHangRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id:"+id));
        khachHangRepository.delete(kh);
    }

    //check tt khachhang với id vừa đăng nhập
    @Override
    public boolean daCoThongTinKhachHang(Integer taiKhoanId) {
        return khachHangRepository.existsByAccount_Id(taiKhoanId);
    }
}
