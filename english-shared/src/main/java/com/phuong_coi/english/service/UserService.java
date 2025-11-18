package com.phuong_coi.english.service;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.phuong_coi.english.model.User;
import com.phuong_coi.english.exception.UserException;

@RemoteServiceRelativePath("users")
public interface UserService extends RemoteService {
    User addUser(User user) throws UserException;
    User updateUser(User user, String phoneNumber) throws UserException;
    List<User> getAllUsers();
}
