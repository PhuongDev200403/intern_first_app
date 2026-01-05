package com.phuong_coi.english.servlet;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.entity.Employee;
import com.phuong_coi.english.exception.LoginException;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
import com.phuong_coi.english.service.AuthService;
import com.phuong_coi.english.util.OfyService;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpSession;

public class AuthServiceImpl extends RemoteServiceServlet implements AuthService{

    @Override
    public EmployeeDTO login(String email, String password) throws LoginException {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new LoginException("Email và mật khẩu không được để trống");
        }

        // Tìm employee theo email
        Employee employee = OfyService.ofy().load().type(Employee.class)
                .filter("email", email.trim())
                .first().now();

        System.out.println("Current employee :" + employee.getFullName());
        // Nếu không tìm thấy → báo lỗi rõ ràng
        if (employee == null) {
            throw new LoginException("Email không tồn tại trong hệ thống");
        }

        // Kiểm tra mật khẩu (hiện tại đang lưu plain text – cảnh báo sau)
        if (!employee.getPassword().equals(password)) {
            throw new LoginException("Mật khẩu không chính xác");
        }

        // Đăng nhập thành công → chuyển sang DTO
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setRole(employee.getRole().toString());

        return dto;
    }

    @Override
    public EmployeeDTO register(EmployeeRequest employee) throws LoginException {
        //validate dữ liệu trong form trước

        // kiểm tra email đã tồn tại trong database chưa trước đã
        Employee em = OfyService.ofy().load().type(Employee.class)
                                        .filter("email", employee.getEmail()).first().now();
        if(em != null){
            throw new LoginException("Email đã tồn tại yêu cầu nhập email khác");
        }

        // đổi từ request sang entity
        Employee newEm = new Employee();
        newEm.setEmail(employee.getEmail());
        newEm.setFullName(employee.getFullName());
        newEm.setPassword(employee.getPassword());
        newEm.setPhoneNumber(employee.getPhoneNumber());
        newEm.setRole(employee.getRole().toString());

        //đổi từ entity sang dto
        EmployeeDTO emDto = new EmployeeDTO();
        emDto.setEmail(newEm.getEmail());
        emDto.setFullName(newEm.getFullName());
        emDto.setEmployeeId(newEm.getId());
        emDto.setPhoneNumber(newEm.getPhoneNumber());
        emDto.setRole(newEm.getRole());

        //lưu vào db
        OfyService.ofy().save().entity(newEm).now();
        System.out.println("Đăng ký thành công nhân viên mới :" + emDto.getFullName());
        //Đăng ký thành công chuyển đến trang đăng nhập
        return emDto;
    }
    
}
