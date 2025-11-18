package com.phuong_coi.english.validation;

import java.util.List;

import com.phuong_coi.english.model.User;

public class UserValidation {

    // valid full name: not null and not empty (trimmed)
    public static boolean isValidFullName(String fullName) {
        return fullName != null && !fullName.trim().isEmpty();
    }

    // phoneNumber must be exactly 10 digits (modify if you need other rules)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches("\\d{10}");
    }

    // check if phone exists in provided list (works for server-side list)
    public static boolean isPhoneNumberExists(List<User> userList, String phoneNumber) {
        if (userList == null || phoneNumber == null) {
            return false;
        }
        for (User u : userList) {
            if (phoneNumber.equals(u.getSoDienThoai())) {
                return true;
            }
        }
        return false;
    }
}
