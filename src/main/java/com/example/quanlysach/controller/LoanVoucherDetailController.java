package com.example.quanlysach.controller;

import com.example.quanlysach.dto.request.LoanVoucherDetailRequest;
import com.example.quanlysach.dto.response.CustomerBorrowResponse;
import com.example.quanlysach.dto.response.LoanVoucherDetailResponse;
import com.example.quanlysach.dto.response.BookBorrowResponse;
import com.example.quanlysach.service.loanvoucherdetail.LoanVoucherDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/phieu-muon-chi-tiet")
public class LoanVoucherDetailController {
    @Autowired
    private LoanVoucherDetailService loanVoucherDetailService;

    @GetMapping("/getAll")
    public ResponseEntity<List<LoanVoucherDetailResponse>> getAll() {
        return ResponseEntity.ok(loanVoucherDetailService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody LoanVoucherDetailRequest request) {
        try {
            return ResponseEntity.ok(loanVoucherDetailService.create(request));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message", ex.getReason()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unknown error occurred"));
        }
    }


    @GetMapping("/sach-da-muon/{id}")
    public ResponseEntity<List<BookBorrowResponse>> getBookBorrow(@PathVariable Integer id) {
        return ResponseEntity.ok(loanVoucherDetailService.getBookBorrow(id));
    }
    @GetMapping("/khach-muon-sach/{id}")
    public ResponseEntity<List<CustomerBorrowResponse>> getCustomerBorrow(@PathVariable Integer id) {
        return ResponseEntity.ok(loanVoucherDetailService.getCustomerBorrow(id));
    }
    @PutMapping("/tra-sach/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Integer id, @RequestParam Integer quantity) {
        try {
            loanVoucherDetailService.returnBook(id, quantity);
            return ResponseEntity.ok("Returned books successfully");
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message", ex.getReason()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unknown error occurred"));
        }
    }
}
