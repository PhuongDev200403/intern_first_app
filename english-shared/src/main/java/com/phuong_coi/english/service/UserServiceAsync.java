package com.phuong_coi.english.service;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.model.User;

public interface UserServiceAsync {
    void addUser(User user, AsyncCallback<User> callback);
    void updateUser(User user, String phoneNumber, AsyncCallback<User> callback);
    void getAllUsers(AsyncCallback<List<User>> callback);
}
