package com.phuong_coi.english.presenter;

//import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.event.AppEventBus;
import com.phuong_coi.english.event.UserAddedEvent;
import com.phuong_coi.english.event.UserRemoveEvent;
import com.phuong_coi.english.event.UserSelectedEvent;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.service.UserServiceAsync;
import com.phuong_coi.english.view.UserListView;

public class UserListPresenter {

    private final UserServiceAsync userService = GWT.create(UserService.class);
    private final UserListView view;
    private final UserDetailPresenter userDetailPresenter;

    public UserListPresenter(UserListView view, UserDetailPresenter userDetailPresenter) {
        this.view = view;
        this.userDetailPresenter = userDetailPresenter;
        GWT.log("Hiển thị danh sách lần đầu tiên khi tại giao diện");
        loadUsers();

        //lắng nghe tín hiệu click row
        AppEventBus.get().addHandler(UserSelectedEvent.TYPE, event -> {
            UserDTO userDTO = event.getSelectedUser();
            userDetailPresenter.showDetail(userDTO);

            GWT.log("Đã bắt được tín hiệu click vào một dòng");
        });

        //Lắng nghe sự kiện click tạo user mới nhé 
        AppEventBus.get().addHandler(UserAddedEvent.TYPE, event -> {
            UserDTO userDTO = event.getUser();
            GWT.log("Bắt sự kiện add user mới bên UserlistPresenter");
            loadUsers();
        });

        //Lắng nghe sự kiện click xóa user
        AppEventBus.get().addHandler(UserRemoveEvent.TYPE, event ->{
            UserDTO userDTO = event.getUser();
            if (!Window.confirm("Bạn có chắc muốn xóa user: " + userDTO.getFullName() + "?")) {
                GWT.log("Người dùng đã hủy bỏ lệnh xóa user");
                return;
            }
            
            GWT.log("Bắt tín hiệu xóa user thành công tại UserListPresenter");
            deleteUser(userDTO);
            
        });
    }

    public void loadUsers() {
        userService.getAllUsers(new AsyncCallback<List<UserDTO>>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Error: " + caught.getMessage());
            }
            @Override
            public void onSuccess(List<UserDTO> users) {
                view.showUsers(users);
            }
        });
    }

    public void deleteUser(UserDTO userDto){
        userService.removeUser(userDto.getId(),new AsyncCallback<Void>(){
            @Override
            public void onSuccess(Void result){
                GWT.log("Xóa thành công user :");
                loadUsers();
            }
            @Override
            public void onFailure(Throwable caught){
                GWT.log("có lỗi xảy ra khi xóa " + caught.getMessage());
            }
        });
    }

    public UserListView getView() {
        return view;
    }
}
