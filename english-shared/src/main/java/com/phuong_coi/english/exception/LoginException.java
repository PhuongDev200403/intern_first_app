package com.phuong_coi.english.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginException extends Exception implements IsSerializable{

    public LoginException(){}

    public LoginException(String message){
        super(message);
    }
}
