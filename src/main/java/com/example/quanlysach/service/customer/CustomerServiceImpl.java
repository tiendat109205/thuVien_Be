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
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepoSitory accountRepoSitory;

    public List<CustomerResponse> getAll() {
        List<Customer> list = customerRepository.findAll();

        return list.stream().map(customer -> new CustomerResponse(
                customer.getId(),
                customer.getCustomerCode(),
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAccount() != null ? customer.getAccount().getId() : null
        )).collect(Collectors.toList());
    }



    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        // Lấy người dùng đang đăng nhập
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Tìm tài khoản từ username
        Account account = accountRepoSitory.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = new Customer();
        customer.setCustomerCode(customerRequest.getCustomerCode());
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setAddress(customerRequest.getAddress());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setAccount(account);
        Customer save = customerRepository.save(customer);
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
    public CustomerResponse updateCustomer(Integer id, CustomerRequest customerRequest) {
        //Lấy người dùng hiện tại từ token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepoSitory.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        //Lấy khách hàng cần cập nhật
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No customer found with ID: " + id));
        //Kiểm tra quyền truy cập
        boolean isAdmin = account.getRole().equals("ROLE_ADMIN");
        if (!isAdmin && !customer.getAccount().getId().equals(account.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to update this customer information..");
        }
        //Cập nhật thông tin
        customer.setCustomerCode(customerRequest.getCustomerCode());
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setAddress(customerRequest.getAddress());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        Customer update = customerRepository.save(customer);
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
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()->new RuntimeException("No id found:"+id));
        customerRepository.delete(customer);
    }

    //check tt khachhang với id vừa đăng nhập
    @Override
    public boolean checkInfomation(Integer accountId) {
        return customerRepository.existsByAccount_Id(accountId);
    }
}
