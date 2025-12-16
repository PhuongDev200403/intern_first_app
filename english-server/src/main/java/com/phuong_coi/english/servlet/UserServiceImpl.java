package com.phuong_coi.english.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.util.OfyService;
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

        // Check xem số điện thoại đã tồn tại hay chưa
        List<UserDTO> users = getAllUsers();

        if (UserValidation.isPhoneNumberExists(users, user.getSoDienThoai())) {
            throw new UserException("Số điện thoại đã tồn tại");
        }

        // Đổi từ UserDTO sang User entity
        User entity = new User();
        entity.setFullName(user.getFullName());
        entity.setSoDienThoai(user.getSoDienThoai());
        entity.setPhongBan(user.getPhongBan());
        entity.setChucVu(user.getChucVu());
        entity.setNgayVao(user.getNgayVao());

        // Lưu vào local database
        OfyService.ofy().save().entity(entity).now();
        user.setId((Long) entity.getId());

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

        // Find user by phone number
        List<User> users = OfyService.ofy().load().type(User.class)
                .filter("soDienThoai", phoneNumber)
                .list();
        if (users == null || users.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User existingUser = users.get(0);
        // Update fields
        existingUser.setFullName(user.getFullName());
        existingUser.setPhongBan(user.getPhongBan());
        existingUser.setChucVu(user.getChucVu());
        existingUser.setNgayVao(user.getNgayVao());

        // Save to Datastore
        OfyService.ofy().save().entity(existingUser).now();

        user.setId(existingUser.getId());
        user.setSoDienThoai(existingUser.getSoDienThoai());

        System.out.println("✓ User updated in Datastore: " + existingUser.getFullName());

        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> USERS = OfyService.ofy().load().type(User.class).list();
        List<UserDTO> USERS_DTO = new ArrayList<>();
        for (User user : USERS) {
            UserDTO userDto = new UserDTO();
            userDto.setId(user.getId());
            userDto.setFullName(user.getFullName());
            userDto.setSoDienThoai(user.getSoDienThoai());
            userDto.setPhongBan(user.getPhongBan());
            userDto.setChucVu(user.getChucVu());
            userDto.setNgayVao(user.getNgayVao());
            USERS_DTO.add(userDto);
        }
        return USERS_DTO;
    }

    @Override
    public void removeUser(Long userId) throws UserException {
        //check xem user có tồn tại không đã
        if (userId == null) {
            throw new UserException("User ID cannot be null");
        }
    
        OfyService.ofy().delete().type(User.class).id(userId).now();
    }
}
