package com.phuong_coi.english.presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.event.AppEventBus;
import com.phuong_coi.english.event.UserUpdatedEvent;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.service.UserServiceAsync;


public class UserDetailPresenter {

    public interface DetailPopup {
        HasClickHandlers getCloseButton();
        HasClickHandlers getUpdateButton();

        void setFullName(String fullName);
        void setSoDienThoai(String soDienThoai);
        void setPhongBan(String phongBan);
        void setChucVu(String chucVu);
        void setNgayVao(Date ngayVao);

        String getFullName();
        String getSoDienThoai();
        String getPhongBan();
        String getChucVu();
        Date getNgayVao();

        void show(); //Show popup
        void hide(); // ẩn popup
        void center(); //Căn giữa popup ra màn hình
        void showMessage(String message);
    }

    private final DetailPopup view;
    private UserDTO currentUser = null;

    private final UserServiceAsync usService = GWT.create(UserService.class);

    public UserDetailPresenter(DetailPopup view){
        this.view = view;
        bind();
    }

    public void bind(){
        view.getCloseButton().addClickHandler(event -> view.hide()); // đóng popup chi tiết
        view.getUpdateButton().addClickHandler(event -> onUpdateClicked());
    }

    // Cập nhật user khi xem chi tiết
    private void onUpdateClicked(){
        if(currentUser == null){
            view.showMessage("Vui lòng chọn user trước khi cập nhật");
        }

        currentUser.setFullName(view.getFullName());
        currentUser.setPhongBan(view.getPhongBan());
        currentUser.setChucVu(view.getChucVu());
        currentUser.setNgayVao(view.getNgayVao());

        // call RPC
        usService.updateUser(currentUser, currentUser.getSoDienThoai(), new AsyncCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO result){
                AppEventBus.get().fireEvent(new UserUpdatedEvent(result));
                GWT.log("Phát tín hiệu cập nhật user ở UserDetailPresenter đi nhé ai bắt thì bắt");
                view.hide();
            }

            @Override
            public void onFailure(Throwable caught) {
                view.showMessage("có lỗi khi cập nhật :" + caught.getMessage());
            }
        });

    }

    public void showDetail(UserDTO user) {
        if (user == null) return;

        currentUser = user;

        view.setFullName(user.getFullName());
        view.setSoDienThoai(user.getSoDienThoai());
        view.setPhongBan(user.getPhongBan());
        view.setChucVu(user.getChucVu());
        view.setNgayVao(user.getNgayVao());

        view.center();// Căn giữa popup
        view.show();
    }
}