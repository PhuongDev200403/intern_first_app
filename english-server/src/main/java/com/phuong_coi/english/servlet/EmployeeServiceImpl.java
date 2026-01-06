package com.phuong_coi.english.servlet;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.entity.Employee;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
import com.phuong_coi.english.service.EmployeeService;
import com.phuong_coi.english.util.OfyService;

public class EmployeeServiceImpl extends RemoteServiceServlet implements EmployeeService {

    @Override
    public EmployeeDTO createEm(EmployeeRequest employee) throws Exception {
        if (employee.getEmail().isEmpty()) {
            throw new Exception("Email không hợp lệ");
        }

        if (employee.getFullName().isEmpty()) {
            throw new Exception("FullName không hợp lệ");
        }

        if (employee.getPhoneNumber().isEmpty()) {
            throw new Exception("Số điện thoại không hợp lệ");
        }

        // đổi từ request sang entity
        Employee em = new Employee();
        em.setEmail(employee.getEmail());
        em.setFullName(employee.getFullName());
        em.setPhoneNumber(employee.getPhoneNumber());
        em.setPassword(employee.getPassword());
        em.setRole(employee.getRole());

        // Lưu vào db
        OfyService.ofy().save().entity(em).now();

        // Đổi từ entity sang dto
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
        if (employee == null) {
            throw new Exception("Dữ liệu nhân viên cập nhật không hợp lệ");
        }
        if (employeeId == null || employeeId <= 0) {
            throw new Exception("ID nhân viên không hợp lệ");
        }

        // Bước 1: Tìm entity cũ theo ID
        Employee existingEmployee = OfyService.ofy().load().type(Employee.class)
                .id(employeeId).now();

        if (existingEmployee == null) {
            throw new Exception("Không tìm thấy nhân viên với ID: " + employeeId);
        }

        // Bước 2: Cập nhật các field từ DTO mới (tránh cập nhật ID và email nếu không
        // muốn)
        // Bạn có thể cho phép hoặc không cho phép thay đổi email tùy yêu cầu
        if (employee.getFullName() != null && !employee.getFullName().trim().isEmpty()) {
            existingEmployee.setFullName(employee.getFullName().trim());
        }

        if (employee.getPhoneNumber() != null) {
            existingEmployee.setPhoneNumber(employee.getPhoneNumber().trim());
        }

        if (employee.getRole() != null && !employee.getRole().trim().isEmpty()) {
            existingEmployee.setRole(employee.getRole().trim());
        }

        // Bước 3: Lưu lại entity đã cập nhật
        OfyService.ofy().save().entity(existingEmployee).now();

        // Bước 4: Trả về DTO mới nhất (có thể load lại để chắc chắn)
        EmployeeDTO updatedDTO = new EmployeeDTO();
        updatedDTO.setEmployeeId(existingEmployee.getId());
        updatedDTO.setFullName(existingEmployee.getFullName());
        updatedDTO.setEmail(existingEmployee.getEmail());
        updatedDTO.setPhoneNumber(existingEmployee.getPhoneNumber());
        updatedDTO.setRole(existingEmployee.getRole());

        return updatedDTO;
    }

    @Override
    public void deleteEm(Long employeeId) throws Exception {
        // tìm kiếm xem nhân viên muốn cập nhật có đang tồn tại hay không
        Employee employee = OfyService.ofy().load().type(Employee.class)
                .id(employeeId).now();
        if (employee == null) {
            throw new Exception("Nhân viên đang cần cập nhật không tồn tại");
        }
        // Xóa nhân viên được chọn khỏi danh sách
        OfyService.ofy().delete().entity(employee).now();
    }

    @Override
    public List<EmployeeDTO> getAll() throws Exception {
        List<Employee> employees = OfyService.ofy().load().type(Employee.class).list();
        System.out.println("list employee :" + employees);
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmployeeId(employee.getId());
            dto.setEmail(employee.getEmail());
            dto.setFullName(employee.getFullName());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setRole(employee.getRole());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public EmployeeDTO getByEmployeeId(Long employeeId) throws Exception {
        // tìm xem có tồn tại nhân viên này hay không
        return null;
    }

    @Override
    public List<EmployeeDTO> search(String keyword) throws Exception {
        String word = keyword.trim().toLowerCase();
        // Toàn bộ danh sách nhân viên
        List<Employee> employees = OfyService.ofy().load().type(Employee.class).list();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee em : employees) {
            boolean isContaint = false;

            if (em.getEmail().toLowerCase().contains(word)) {
                isContaint = true;
            }
            if (em.getFullName().toLowerCase().contains(word)) {
                isContaint = true;
            }
            if (em.getPhoneNumber().toLowerCase().contains(word)) {
                isContaint = true;
            }
            if (isContaint) {
                dtos.add(entityToDto(em));
            }
        }
        return dtos;
    }

    @Override
    public List<EmployeeDTO> getListByRole(String keyword) throws Exception {
        System.out.println("filter by :" + keyword);
        List<EmployeeDTO> dtos = new ArrayList<>();
        if (keyword.equals("All")) {
            dtos = getAll();
        } else {
            List<Employee> ems = OfyService.ofy().load().type(Employee.class)
                    .filter("role", keyword).list();
            for (Employee employee : ems) {
                dtos.add(entityToDto(employee));
            }
        }
        return dtos;
    }

    // Chuyển từ entity sang dto
    private EmployeeDTO entityToDto(Employee em) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(em.getId());
        dto.setEmail(em.getEmail());
        dto.setFullName(em.getFullName());
        dto.setPhoneNumber(em.getPhoneNumber());
        dto.setRole(em.getRole());
        return dto;
    }

    @Override
    public List<EmployeeDTO> sortByName(String keyword) throws Exception{
        List<EmployeeDTO> dtos = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        if(keyword.equals("Tăng dần")){
            employees = OfyService.ofy().load().type(Employee.class).order("fullName").list();
        }else if (keyword.equals("Giảm dần")) {
            employees = OfyService.ofy().load().type(Employee.class).order("-fullName").list();
        }else {
            dtos = getAll();
        }
        
        if(employees != null || !employees.isEmpty()){
            //Kiểm tra danh sách có rỗng không đã
            for (Employee employee : employees) {
                System.out.println("Bộ lọc khác all nên đi vào đây");
                dtos.add(entityToDto(employee));
            }
        }
        return dtos;
    }
}
