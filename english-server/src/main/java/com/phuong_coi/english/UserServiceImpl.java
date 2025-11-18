package com.phuong_coi.english;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.model.User;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.validation.UserValidation;
import com.phuong_coi.english.exception.UserException;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

    private static final List<User> USERS = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User addUser(User user) throws UserException {
        if (user == null) {
            throw new UserException("User can't be null");
        }

        if (!UserValidation.isValidFullName(user.getFullName())) {
            throw new UserException("Name can't be null or empty");
        }

        if (!UserValidation.isValidPhoneNumber(user.getSoDienThoai())) {
            throw new UserException("Phone number must contain exactly 10 digits");
        }

        // check duplicate
        if (UserValidation.isPhoneNumberExists(USERS, user.getSoDienThoai())) {
            throw new UserException("Phone number already existed");
        }

        // add to server list (clone or use directly)
        USERS.add(user);
        return user;
    }

    @Override
    public User updateUser(User user, String phoneNumber) throws UserException {
        if (user == null) {
            throw new UserException("User can't be null");
        }

        if (!UserValidation.isValidFullName(user.getFullName())) {
            throw new UserException("Name can't be null or empty");
        }

        // Tìm user theo số điện thoại cũ
        User existingUser = null;
        for (User u : USERS) {
            if (phoneNumber.equals(u.getSoDienThoai())) {
                existingUser = u;
                break;
            }
        }

        if (existingUser == null) {
            throw new UserException("User not found");
        }

        // Update thông tin (giữ nguyên số điện thoại)
        existingUser.setFullName(user.getFullName());
        existingUser.setPhongBan(user.getPhongBan());
        existingUser.setChucVu(user.getChucVu());
        existingUser.setNgayVao(user.getNgayVao());

        return existingUser;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(USERS);
    }
}
