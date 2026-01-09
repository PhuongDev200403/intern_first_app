package com.phuong_coi.english.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.phuong_coi.english.exception.EmployeeException;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
@RemoteServiceRelativePath("employee")
public interface EmployeeService extends RemoteService{
    EmployeeDTO createEm(EmployeeRequest employee) throws EmployeeException;
    EmployeeDTO updateEm(EmployeeDTO employee, Long employeeId) throws EmployeeException;
    void deleteEm(Long employeeId) throws EmployeeException;
    List<EmployeeDTO> getAll() throws EmployeeException;
    //EmployeeDTO getByEmployeeId(Long employeeId) throws Exception;
    //Chức năng tìm kiếm nhân viên theo tên;
    //List<EmployeeDTO> search(String keyword) throws Exception;
    //Chức năng tìm kiếm nhân viên theo email;
    List<EmployeeDTO> searchByEmail(String email) throws EmployeeException;
    //Chức năng tìm kiếm nhân viên theo số điện thoại;
    List<EmployeeDTO> searchByPhoneNumber(String phoneNumber) throws EmployeeException;
    //Lọc theo quyền truy cập
    List<EmployeeDTO> getListByRole(String keyword) throws EmployeeException;
    //Sắp xếp tăng dần theo tên từ Z - A
    List<EmployeeDTO> sortByName(String keyword) throws EmployeeException;
}
