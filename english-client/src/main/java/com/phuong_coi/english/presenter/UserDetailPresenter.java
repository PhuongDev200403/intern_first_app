package com.phuong_coi.english.presenter;

import java.util.Date;

//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
//import com.google.gwt.user.client.Window;
import com.phuong_coi.english.model.User;


public class UserDetailPresenter {

    public interface DetailPopup {
        HasClickHandlers getCloseButton();

        void setFullName(String fullName);
        void setSoDienThoai(String soDienThoai);
        void setPhongBan(String phongBan);
        void setChucVu(String chucVu);
        void setNgayVao(Date ngayVao);

        void show(); //Show popup
        void hide(); // ẩn popup
        void center(); //Căn giữa popup ra màn hình
    }

    private final DetailPopup view;

    public UserDetailPresenter(DetailPopup view){
        this.view = view;
        bind();
    }

    public void bind(){
        view.getCloseButton().addClickHandler(event -> view.hide()); // đóng popup chi tiết
    }

    public void showDetail(User user) {
        if (user == null) return;

        view.setFullName(user.getFullName());
        view.setSoDienThoai(user.getSoDienThoai());
        view.setPhongBan(user.getPhongBan());
        view.setChucVu(user.getChucVu());
        view.setNgayVao(user.getNgayVao());

        view.center();// Căn giữa popup
    }
}