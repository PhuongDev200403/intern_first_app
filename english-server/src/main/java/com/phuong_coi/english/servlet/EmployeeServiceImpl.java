package com.phuong_coi.english.servlet;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.entity.Employee;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
import com.phuong_coi.english.service.EmployeeService;
import com.phuong_coi.english.util.OfyService;

public class EmployeeServiceImpl extends RemoteServiceServlet implements EmployeeService{

    @Override
    public EmployeeDTO createEm(EmployeeRequest employee) throws Exception {
        if(employee.getEmail().isEmpty()){
            throw new Exception("Email không hợp lệ");
        }

        if(employee.getFullName().isEmpty()){
            throw new Exception("FullName không hợp lệ");
        }

        if(employee.getPhoneNumber().isEmpty()){
            throw new Exception("Số điện thoại không hợp lệ");
        }

        //đổi từ request sang entity
        Employee em = new Employee();
        em.setEmail(employee.getEmail());
        em.setFullName(employee.getFullName());
        em.setPhoneNumber(employee.getPhoneNumber());
        em.setPassword(employee.getPassword());
        em.setRole(employee.getRole());

        //Lưu vào db
        OfyService.ofy().save().entity(em).now();

        //Đổi từ entity sang dto
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(em.getId());
        dto.setEmail(em.getEmail());
        dto.setFullName(em.getFullName());
        dto.setPhoneNumber(em.getPhoneNumber());
        dto.setRole(em.getRole());

        return dto;
    }

    @Override
    public EmployeeDTO updateEm(EmployeeDTO employee, Long employeeId) throws Exception {
        return null;
    }

    @Override
    public void deleteEm(Long employeeId) throws Exception {
        // tìm kiếm xem nhân viên muốn cập nhật có đang tồn tại hay không
        Employee employee = OfyService.ofy().load().type(Employee.class)
                                            .filter("employeeId", employeeId).first().now();
        if(employee == null){
            throw new Exception("Nhân viên đang cần cập nhật không tồn tại");
        }
        //Xóa nhân viên được chọn khỏi danh sách
        OfyService.ofy().delete().entity(employee).now();
    }

    @Override
    public List<EmployeeDTO> getAll() throws Exception {
        List<Employee> employees = OfyService.ofy().load().type(Employee.class).list();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmployeeId(employee.getId());
            dto.setEmail(employee.getEmail());
            dto.setFullName(employee.getFullName());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setRole(employee.getRole());
        }
        return dtos;
    }

    @Override
    public EmployeeDTO getByEmployeeId(Long employeeId) throws Exception {
        // tìm xem có tồn tại nhân viên này hay không
        return null;
    }

    
}
