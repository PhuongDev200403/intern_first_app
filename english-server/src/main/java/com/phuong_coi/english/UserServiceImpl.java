package com.phuong_coi.english;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.validation.UserValidation;
import com.phuong_coi.english.entity.User;
import com.phuong_coi.english.exception.UserException;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

    private static final List<UserDTO> USERS = Collections.synchronizedList(new ArrayList<>());

    @Override
    public UserDTO addUser(UserDTO user) throws UserException {
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

        // List<User> USERS = OfyService.ofy().load().type(User.class).list();
        if (UserValidation.isPhoneNumberExists(USERS, user.getSoDienThoai())) {
            throw new UserException("Phone number already existed");
        }

        // đổi từ UserDTO sang User entity
        // User entity = new User();
        // entity.setFullName(user.getFullName());
        // entity.setSoDienThoai(user.getSoDienThoai());
        // entity.setPhongBan(user.getPhongBan());
        // entity.setChucVu(user.getChucVu());
        // entity.setNgayVao(user.getNgayVao());

        // // //Lưu vào local database
        // OfyService.ofy().save().entity(entity).now();
        // user.setId((Long) entity.getId());
        user.setId(1L);

        USERS.add(user);
        return user;
    }

    @Override
    public UserDTO updateUser(UserDTO user, String phoneNumber) throws UserException {
        if (user == null) {
            throw new UserException("User can't be null");
        }

        if (!UserValidation.isValidFullName(user.getFullName())) {
            throw new UserException("Name can't be null or empty");
        }

        // Tìm user theo số điện thoại cũ
        UserDTO existingUser = null;
        for (UserDTO u : USERS) {
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

        // return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // List<User> USERS = OfyService.ofy().load().type(User.class).list();
        // List<UserDTO> USERS_DTO = new ArrayList<>();
        // for(User user : USERS){
        //     UserDTO userDto = new UserDTO();
        //     userDto.setId(user.getId());
        //     userDto.setFullName(user.getFullName());
        //     userDto.setSoDienThoai(user.getSoDienThoai());
        //     userDto.setPhongBan(user.getPhongBan());
        //     userDto.setChucVu(user.getChucVu());
        //     userDto.setNgayVao(user.getNgayVao());

        //     USERS_DTO.add(userDto);
        // }

        // System.out.println("Danh sách users được lưu trong local :" + USERS_DTO);
        // return USERS_DTO;

        System.out.println("Danh sách Users :" + USERS);
        return new ArrayList<>(USERS);
    }
}
