package com.phuong_coi.english.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;

public interface AuthServiceAsync {
    void login(String email, String password, AsyncCallback<EmployeeDTO> callback);
    void register(EmployeeRequest employee, AsyncCallback<EmployeeDTO> callback);
}
