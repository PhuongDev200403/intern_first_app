package com.phuong_coi.english.model;

import java.io.Serializable;
import java.util.Date;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements Serializable, IsSerializable {
    private static final long serialVersionUID = 1L;
    
    private String fullName;
    private String soDienThoai;
    private String phongBan;
    private String chucVu;
    private Date ngayVao; // java.util.Date -> serializable in GWT

    // Constructor không tham số bắt buộc cho GWT RPC
    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public Date getNgayVao() {
        return ngayVao;
    }

    public void setNgayVao(Date ngayVao) {
        this.ngayVao = ngayVao;
    }
}
