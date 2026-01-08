package com.phuong_coi.english.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;

public interface EmployeeServiceAsync {
    void createEm(EmployeeRequest employee, AsyncCallback<EmployeeDTO> callback);
    void updateEm(EmployeeDTO employee, Long employeeId, AsyncCallback<EmployeeDTO> callback);
    void getAll(AsyncCallback<List<EmployeeDTO>> callback);
    void deleteEm(Long employeeId, AsyncCallback<Void> callback);
    //void getByEmployeeId(Long employeeId, AsyncCallback<EmployeeDTO> callback);
    void search(String keyword, AsyncCallback<List<EmployeeDTO>> callback);
    void searchByEmail(String email, AsyncCallback<List<EmployeeDTO>> callback);
    void searchByPhoneNumber(String phoneNumber, AsyncCallback<List<EmployeeDTO>> callback);
    void getListByRole(String keyword, AsyncCallback<List<EmployeeDTO>> callback);
    void sortByName(String keyword, AsyncCallback<List<EmployeeDTO>> callback);
}

