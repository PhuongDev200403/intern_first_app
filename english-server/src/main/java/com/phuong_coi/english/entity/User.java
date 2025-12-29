package com.phuong_coi.english.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity(name = "users")
public class User {
    @Id
    Long id;

    @Index
    private String fullName;
    @Index
    private String soDienThoai;
    private String phongBan;
    private String chucVu;
    private Date ngayVao;

    //private Role role;



    // Constructor không tham số
    public User() {}

    // Constructor có tham số
    public User(Long id, String fullName, String soDienThoai, String phongBan, String chucVu, Date ngayVao) {
        this.id = id;
        this.fullName = fullName;
        this.soDienThoai = soDienThoai;
        this.phongBan = phongBan;
        this.chucVu = chucVu;
        this.ngayVao = ngayVao;
    }

    // Getter và Setter cho id
    public Long getId() {
        return id;
    }

    // Getter và Setter cho fullName
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter và Setter cho soDienThoai
    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    // Getter và Setter cho phongBan
    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    // Getter và Setter cho chucVu
    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    // Getter và Setter cho ngayVao
    public Date getNgayVao() {
        return ngayVao;
    }

    public void setNgayVao(Date ngayVao) {
        this.ngayVao = ngayVao;
    }
}
