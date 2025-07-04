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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "phieu_muon_chi_tiet")
@AllArgsConstructor
@NoArgsConstructor
public class PhieuMuonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phieu_muon_id", nullable = false)
    private PhieuMuon phieuMuon;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sach_id", nullable = false)
    private Sach sach;

    @NotNull
    @Column(name = "ngay_het_han", nullable = false)
    private Date ngayHetHan;

    @NotNull
    @Column(name = "so_luong_sach_muon")
    private Integer soLuongSachMuon;
}