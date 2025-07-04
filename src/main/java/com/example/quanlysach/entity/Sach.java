package com.example.quanlysach.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Getter
@Setter
@Entity
@Table(name = "sach")
@AllArgsConstructor
@NoArgsConstructor
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @Nationalized
    @Column(name = "ma_sach", length = 10)
    private String maSach;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "ten_sach", nullable = false)
    private String tenSach;

    @Size(max = 100)
    @Nationalized
    @Column(name = "tac_gia", length = 100)
    private String tacGia;

    @Size(max = 100)
    @Nationalized
    @Column(name = "nha_xuat_ban", length = 100)
    private String nhaXuatBan;

    @Column(name = "nam_phat_hanh")
    private Integer namPhatHanh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "the_loai_id")
    private TheLoai theLoai;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Boolean trangThai;

    @Column(name = "so_luong")
    private Integer soLuong;
}