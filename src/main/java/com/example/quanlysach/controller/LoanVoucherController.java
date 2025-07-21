package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.LoanVoucherRequest;
import com.example.quanlysach.dto.response.LoanVoucherResponse;
import com.example.quanlysach.service.loanvoucher.LoanVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/phieu-muon")
public class LoanVoucherController {
    @Autowired
    LoanVoucherService loanVoucherService;

    @GetMapping("/getAll")
    public List<LoanVoucherResponse> getAll() {
        return loanVoucherService.getAll();
    }
    @PostMapping("/add")
    public ResponseEntity<?> createLoanVoucher(@RequestBody LoanVoucherRequest request) {
        try {
            LoanVoucherResponse response = loanVoucherService.create(request);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message", ex.getReason()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unknown error occurred"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody LoanVoucherRequest loanVoucherRequest) {
        LoanVoucherResponse update = loanVoucherService.update(id, loanVoucherRequest);
        return ResponseEntity.ok(update);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        loanVoucherService.delete(id);
        return ResponseEntity.ok().build();
    }
}
