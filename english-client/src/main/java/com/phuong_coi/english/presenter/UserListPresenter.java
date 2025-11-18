package com.phuong_coi.english.presenter;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.model.User;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.service.UserServiceAsync;
import com.phuong_coi.english.view.UserListView;

public class UserListPresenter {
    private final UserServiceAsync userService = GWT.create(UserService.class);
    private final UserListView view;

    public UserListPresenter(UserListView view) {
        this.view = view;
    }

    public void loadUsers() {
        userService.getAllUsers(new AsyncCallback<List<User>>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Error: " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<User> users) {
                view.showUsers(users);
            }
        });
    }

    public UserListView getView() {
        return view;
    }
}
