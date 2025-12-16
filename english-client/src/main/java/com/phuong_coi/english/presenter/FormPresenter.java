package com.phuong_coi.english.presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.event.AppEventBus;
import com.phuong_coi.english.event.UserAddedEvent;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.service.UserServiceAsync;
import com.phuong_coi.english.validation.UserValidation;

public class FormPresenter {

    public interface Display {
        HasClickHandlers getAddButton(); 
        //HasClickHandlers getUpdateButton();

        String getFullName();
        String getSoDienThoai();
        String getPhongBan(); 
        String getChucVu(); 
        Date getNgayVao(); 
        void setFullName(String fullName); 
        void setSoDienThoai(String soDienThoai);

        void setPhongBan(String phongBan);
        void setChucVu(String chucVu);
        void setNgayVao(Date ngayVao);
        void fillUserData(UserDTO user);
        void clearForm();
        void setPhoneNumberEnabled(boolean enabled);
        void showMessage(String message); // alert các thông báo lỗi trên giao diện

        Widget asWidget(); // trả về form hiển thị thông tin
    }

    private final Display view;
    private final UserServiceAsync userService = GWT.create(UserService.class);

    private UserDTO currentUser = null;

    public FormPresenter(Display view) {
        this.view = view;
        bind();
    }

    private void bind() {
        view.getAddButton().addClickHandler(this::onAddClicked);
        //view.getUpdateButton().addClickHandler(this::onUpdateClicked);
    }

    public void setUserForEdit(UserDTO user) {
        this.currentUser = user;
        view.fillUserData(user);
        view.setPhoneNumberEnabled(user == null);
    }

    private void onAddClicked(ClickEvent event) {
        if (!validate())
            return;

        UserDTO newUser = buildUser();
        userService.addUser(newUser, callback("Thêm thành công!"));
    }

    // private void onUpdateClicked(ClickEvent event) {
    //     if (currentUser == null) {
    //         view.showMessage("Vui lòng chọn user từ danh sách để cập nhật!");
    //         return;
    //     }
    //     if (!validate())
    //         return;

    //     UserDTO updatedUser = buildUser();
    //     userService.updateUser(updatedUser, currentUser.getSoDienThoai(), callback("Cập nhật thành công!"));
    // }

    private AsyncCallback<UserDTO> callback(String successMsg) {
        return new AsyncCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO result) {
                GWT.log("Thành công tạo mới user");
                GWT.log("User mới: " + result.getFullName() + " - " + result.getSoDienThoai());

                AppEventBus.get().fireEvent(new UserAddedEvent(result));
                GWT.log("Phát tín hiệu tạo mới một user thành công tại form presenter");
                view.showMessage(successMsg);
                view.clearForm();
                currentUser = null;
            }

            @Override
            public void onFailure(Throwable caught) {
                view.showMessage("Lỗi: " + caught.getMessage());
            }
        };
    }

    private boolean validate() {
        if (view.getFullName().trim().isEmpty()) {
            view.showMessage("Họ tên không được để trống!");
            return false;
        }
        if (!UserValidation.isValidPhoneNumber(view.getSoDienThoai())) {
            view.showMessage("Số điện thoại phải có đúng 10 chữ số!");
            return false;
        }
        return true;
    }

    private UserDTO buildUser() {
        UserDTO u = new UserDTO();
        u.setFullName(view.getFullName().trim());
        u.setSoDienThoai(view.getSoDienThoai().trim());
        u.setPhongBan(view.getPhongBan());
        u.setChucVu(view.getChucVu());
        u.setNgayVao(view.getNgayVao());
        return u;
    }

    public Display getDisplay() {
        return view;
    }
}
