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
        //kiểm tra xem tài khoản có tồn tại không
        //vì email là duy nhất nên tìm email thấy lần đầu tiên là được
        Employee employee = OfyService.ofy().load().type(Employee.class)
                                            .filter("email", email).first().now(); 
        //khi tìm thấy thì kiểm tra xem có đúng mật khẩu hay không
        if (!employee.getPassword().equals(password)) {
            throw new LoginException("Thông tin tài khoản và mật khẩu không chính xác");
        }

        //Lưu vào session
        // HttpServletRequest request = getThreadLocalRequest();
        // HttpSession session = request.getSession(true);
        // session.setAttribute("currentUserId", employee.getId());
        // session.setMaxInactiveInterval(30 * 60);

        // đăng nhập đúng thì vào trang chủ

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getId());
        employeeDTO.setFullName(employee.getFullName());
        employeeDTO.setEmail(email);
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeDTO.setRole(employee.getRole().toString());

        return employeeDTO;
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

        //Đăng ký thành công chuyển đến trang đăng nhập
        return emDto;
    }
    
}
