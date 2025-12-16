package com.phuong_coi.english.servlet;

import java.util.List;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.util.OfyService;
import com.phuong_coi.english.validation.UserValidation;
import com.phuong_coi.english.daos.UserDAO;
import com.phuong_coi.english.entity.User;
import com.phuong_coi.english.exception.UserException;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

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

        // list chỉ có một phần tử nên chỉ lấy phần tử đầu tiên là được
        User existingUser = users.get(0); 

        existingUser.setFullName(user.getFullName());
        existingUser.setPhongBan(user.getPhongBan());
        existingUser.setChucVu(user.getChucVu());
        existingUser.setNgayVao(user.getNgayVao());

        // lưu vào database
        OfyService.ofy().save().entity(existingUser).now();

        user.setId(existingUser.getId());
        user.setSoDienThoai(existingUser.getSoDienThoai());

        System.out.println("✓ User updated in Datastore: " + existingUser.getFullName());

        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<UserDTO> USERS_DTO = UserDAO.getAllUsers();
        return USERS_DTO;
    }

    @Override
    public void removeUser(Long userId) throws UserException {
        //check xem user có tồn tại không đã
        if (userId == null) {
            throw new UserException("User ID cannot be null");
        }

        UserDAO.deleteUserById(userId);
    }
}
