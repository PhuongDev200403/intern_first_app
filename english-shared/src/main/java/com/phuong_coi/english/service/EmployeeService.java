package com.phuong_coi.english.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
@RemoteServiceRelativePath("employee")
public interface EmployeeService extends RemoteService{
    EmployeeDTO createEm(EmployeeRequest employee) throws Exception;
    EmployeeDTO updateEm(EmployeeDTO employee, Long employeeId) throws Exception;
    void deleteEm(Long employeeId) throws Exception;
    List<EmployeeDTO> getAll() throws Exception;
    //EmployeeDTO getByEmployeeId(Long employeeId) throws Exception;
    //Chức năng tìm kiếm nhân viên theo tên;
    List<EmployeeDTO> search(String keyword) throws Exception;
    //Chức năng tìm kiếm nhân viên theo email;
    List<EmployeeDTO> searchByEmail(String email) throws Exception;
    //Chức năng tìm kiếm nhân viên theo số điện thoại;
    List<EmployeeDTO> searchByPhoneNumber(String phoneNumber) throws Exception;
    //Lọc theo quyền truy cập
    List<EmployeeDTO> getListByRole(String keyword) throws Exception;
    //Sắp xếp tăng dần theo tên từ Z - A
    List<EmployeeDTO> sortByName(String keyword) throws Exception;
}
