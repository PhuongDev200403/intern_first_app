package com.phuong_coi.english.validation;



public class EmValidate {
    public static Boolean existedPhoneNumber(String newPhone,String oldPhone){
        return newPhone != oldPhone;
    }
}
