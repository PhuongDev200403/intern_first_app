package com.phuong_coi.english.presenter;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.event.AppEventBus;
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

    public UserListView getView() {
        return view;
    }

}
