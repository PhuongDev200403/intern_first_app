package com.phuong_coi.english.exception;


import com.google.gwt.user.client.rpc.IsSerializable;

public class EmployeeException extends Exception implements IsSerializable{
    private static final long serialVersionUID = 1L;

    public EmployeeException(){

    }

    public EmployeeException(String message){
        super(message);
    }
}
