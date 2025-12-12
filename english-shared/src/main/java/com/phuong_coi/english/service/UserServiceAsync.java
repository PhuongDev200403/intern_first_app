package com.phuong_coi.english.service;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.model.UserDTO;

public interface UserServiceAsync {
    void addUser(UserDTO user, AsyncCallback<UserDTO> callback);
    void updateUser(UserDTO user, String phoneNumber, AsyncCallback<UserDTO> callback);
    void getAllUsers(AsyncCallback<List<UserDTO>> callback);
    void removeUser(Long userId, AsyncCallback<Void> callback);
}
