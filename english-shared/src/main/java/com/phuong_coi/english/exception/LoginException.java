package com.phuong_coi.english.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginException extends Exception implements IsSerializable{
    private static final long serialVersionUID = 1L;  // ← THÊM DÒNG NÀY
    public LoginException(){}

    public LoginException(String message){
        super(message);
    }
}
