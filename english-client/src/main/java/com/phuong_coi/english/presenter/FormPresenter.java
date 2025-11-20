package com.phuong_coi.english.presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.model.User;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.service.UserServiceAsync;
import com.phuong_coi.english.validation.UserValidation;

public class FormPresenter {

    public interface Display {
        HasClickHandlers getAddButton(); // Lấy nút add ra để gắn sự kiện
        HasClickHandlers getUpdateButton(); // Lấy nút update để gắn sự kiện

        String getFullName(); // Lấy thông tin full name từ ô input
        String getSoDienThoai(); // Lấy thông tin số điện thoại từ ô input
        String getPhongBan(); // Lấy thông tin phong ban từ ô input
        String getChucVu(); // Lấy chức vụ từ ô input
        Date getNgayVao();  // Lấy ngày vào từ ô input

        void setFullName(String fullName); //Gán dữ liệu lại cho ô input
        void setSoDienThoai(String soDienThoai); //Gán dữ liệu số điện thoại lại cho ô input
        void setPhongBan(String phongBan);
        void setChucVu(String chucVu);
        void setNgayVao(Date ngayVao);

        void fillUserData(User user);
        void clearForm();
        void setPhoneNumberEnabled(boolean enabled);
        void showMessage(String message); // alert các thông báo lỗi trên giao diện

        Widget asWidget(); // trả về form hiển thị thông tin
    }

    private final Display view;
    private final UserServiceAsync userService = GWT.create(UserService.class);
    private final UserListPresenter userListPresenter;

    private User currentUser = null; // User đang được chỉnh sửa

    public FormPresenter(Display view, UserListPresenter userListPresenter) {
        this.view = view;
        this.userListPresenter = userListPresenter;
        bind();
    }

    private void bind() {
        view.getAddButton().addClickHandler(this::onAddClicked);
        view.getUpdateButton().addClickHandler(this::onUpdateClicked);
    }

    public void setUserForEdit(User user) {
        this.currentUser = user;
        view.fillUserData(user);
        view.setPhoneNumberEnabled(user == null); // disable khi edit
    }

    private void onAddClicked(ClickEvent event) {
        if (!validate()) return;

        User newUser = buildUser();
        userService.addUser(newUser, callback("Thêm thành công!"));
    }

    private void onUpdateClicked(ClickEvent event) {
        if (currentUser == null) {
            view.showMessage("Vui lòng chọn user từ danh sách để cập nhật!");
            return;
        }
        if (!validate()) return;

        User updatedUser = buildUser();
        userService.updateUser(updatedUser, currentUser.getSoDienThoai(), callback("Cập nhật thành công!"));
    }

    private AsyncCallback<User> callback(String successMsg) {
        return new AsyncCallback<User>() {
            @Override public void onSuccess(User result) {
                view.showMessage(successMsg);
                view.clearForm();
                currentUser = null;
                userListPresenter.loadUsers();
            }
            @Override public void onFailure(Throwable caught) {
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

    private User buildUser() {
        User u = new User();
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
