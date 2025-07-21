package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.CustomerRequest;
import com.example.quanlysach.dto.response.CustomerResponse;
import com.example.quanlysach.entity.Account;
import com.example.quanlysach.repository.AccountRepoSitory;
import com.example.quanlysach.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/khach-hang")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    private AccountRepoSitory accountRepoSitory;

    @GetMapping("/getAll")
    public List<CustomerResponse> getAll() {
        return customerService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse add = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(add);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customerRequest, @PathVariable Integer id) {
        try {
            CustomerResponse update = customerService.updateCustomer(id, customerRequest);
            return ResponseEntity.ok(update);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message", ex.getReason()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unknown error occurred"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-thong-tin")
    public ResponseEntity<?> checkInfomation() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> account = accountRepoSitory.findByUsername(username);
        if (account.isEmpty()) return ResponseEntity.ok(false);

        boolean exists = customerService.checkInfomation(account.get().getId());
        return ResponseEntity.ok(exists);
    }

}
