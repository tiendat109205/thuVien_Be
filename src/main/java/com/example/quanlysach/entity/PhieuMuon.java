package com.example.quanlysach.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "phieu_muon")
@AllArgsConstructor
@NoArgsConstructor
public class PhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private KhachHang khachHang;

    @Size(max = 10)
    @Nationalized
    @Column(name = "ma_phieu_muon", length = 10)
    private String maPhieuMuon;

    @NotNull
    @Column(name = "ngay_muon", nullable = false)
    private Date ngayMuon;

    @Column(name = "ngay_tra")
    private Date ngayTra;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Boolean trangThai;

}