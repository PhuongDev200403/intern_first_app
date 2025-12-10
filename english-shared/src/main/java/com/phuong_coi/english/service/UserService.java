package com.phuong_coi.english.service;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.exception.UserException;

@RemoteServiceRelativePath("users")
public interface UserService extends RemoteService {
    UserDTO addUser(UserDTO user) throws UserException;
    UserDTO updateUser(UserDTO user, String phoneNumber) throws UserException;
    List<UserDTO> getAllUsers();
}
