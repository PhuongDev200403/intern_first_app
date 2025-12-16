package com.phuong_coi.english.daos;

import java.util.List;
import java.util.stream.Collectors;

import com.phuong_coi.english.entity.User;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.util.OfyService;

public class UserDAO {
    // DAO sẽ chức tất cả các phương thức, nó giống như cái repository trong spring boot vậy
    public static List<UserDTO> getAllUsers(){
        List<User> users = OfyService.ofy().load().type(User.class).list();

        return users.stream().map(UserDAO::toDTO).collect(Collectors.toList());
    }

    public static UserDTO getById(Long Id){
        User user = OfyService.ofy().load().type(User.class).id(Id).now();
        return user != null ? toDTO(user) : null;
    }

    public static void deleteUserById(Long Id){
        OfyService.ofy().delete().type(User.class).id(Id).now();
    }

    //map từ entity sang dto
    public static UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setSoDienThoai(user.getSoDienThoai());
        userDTO.setPhongBan(user.getPhongBan());
        userDTO.setChucVu(user.getChucVu());
        userDTO.setNgayVao(user.getNgayVao());

        return userDTO;
    }
}
