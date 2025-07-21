package com.example.quanlysach.repository;

import com.example.quanlysach.dto.response.CustomerBorrowResponse;
import com.example.quanlysach.dto.response.LoanVoucherDetailResponse;
import com.example.quanlysach.dto.response.BookBorrowResponse;
import com.example.quanlysach.entity.LoanVoucherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanVoucherDetailRepository extends JpaRepository<LoanVoucherDetail, Integer> {
    @Query("""
        SELECT new com.example.quanlysach.dto.response.LoanVoucherDetailResponse(
            pmct.id,
            pm.id,
            s.id,
            kh.customerName,
            s.bookName,
            pm.borrowDate,
            pm.returnDate,
            s.quantity,
            pmct.numberBookBorrow
        )
        FROM LoanVoucherDetail pmct
        JOIN pmct.loanVoucher pm
        JOIN pm.customer kh
        JOIN pmct.book s
    """)
    List<LoanVoucherDetailResponse> getAllLoanVoucherDetail();

    @Query("""
        SELECT new com.example.quanlysach.dto.response.BookBorrowResponse(
            pmct.id,s.bookCode, s.bookName, s.author, s.publisher,pm.borrowDate,pmct.expiryDate,pmct.numberBookBorrow
        )
        FROM LoanVoucherDetail pmct
        JOIN pmct.book s
        JOIN pmct.loanVoucher pm
        JOIN pm.customer kh
        WHERE kh.id = :customerId
    """)
    List<BookBorrowResponse> getBookBorrow(@Param("customerId") Integer customerId);

    @Query("""
    SELECT new com.example.quanlysach.dto.response.CustomerBorrowResponse(
            kh.customerCode, kh.customerName, kh.email, kh.phoneNumber, pm.borrowDate, pm.returnDate
        )
        FROM LoanVoucherDetail pmct
        JOIN pmct.loanVoucher pm
        JOIN pm.customer kh
        WHERE pmct.book.id = :bookId
    """)
    List<CustomerBorrowResponse> getCustomerBorrow(@Param("bookId") Integer bookId);
    boolean existsByLoanVoucherId(Integer loanVoucherId);

    boolean existsByLoanVoucherIdAndBookId(Integer loanVoucherId, Integer bookId);

    Optional<LoanVoucherDetail> findByLoanVoucherIdAndBookId(Integer loanVoucherId, Integer bookId);

}
