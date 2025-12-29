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
    EmployeeDTO getByEmployeeId(Long employeeId) throws Exception;
}
