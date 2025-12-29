package com.phuong_coi.english.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.phuong_coi.english.exception.LoginException;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService{
    //Phương thức đăng nhập
    EmployeeDTO login(String email, String password) throws LoginException;

    //Phương thức đăng ký
    EmployeeDTO register(EmployeeRequest employee) throws LoginException;
}
