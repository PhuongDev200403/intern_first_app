package com.phuong_coi.english.servlet;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;
import com.phuong_coi.english.entity.Employee;
import com.phuong_coi.english.exception.EmployeeException;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
import com.phuong_coi.english.service.EmployeeService;
import com.phuong_coi.english.util.OfyService;
import com.phuong_coi.english.validation.EmValidate;

public class EmployeeServiceImpl extends RemoteServiceServlet implements EmployeeService {

    @Override
    public EmployeeDTO createEm(EmployeeRequest employee) throws EmployeeException {
        if (employee.getEmail().isEmpty()) {
            throw new EmployeeException("Email không hợp lệ");
        }

        if (employee.getFullName().isEmpty()) {
            throw new EmployeeException("FullName không hợp lệ");
        }

        if (employee.getPhoneNumber().isEmpty()) {
            throw new EmployeeException("Số điện thoại không hợp lệ");
        }

        Employee existedPhone = OfyService.ofy().load().type(Employee.class).filter("phoneNumber", employee.getPhoneNumber()).first().now();

        System.out.println("existed phone number: " + existedPhone);

        if (EmValidate.existedPhoneNumber(employee.getPhoneNumber(), existedPhone.getPhoneNumber())) {
            throw new EmployeeException("Số điện thoại đã tồn tại");
        }
        // đổi từ request sang entity
        Employee em = new Employee();
        em = requestToEntity(employee);

        //Tách tên thành từng ký tự
        String[] words = em.getFullName().toLowerCase().trim().split(" "); // Tách theo khoảng trắng
        List<String> searchWords = new ArrayList<>();
        for (String string : words) {
            searchWords.add(removeVietnameseAccents(string));
        }

        em.setName(removeVietnameseAccents(words[words.length - 1].toLowerCase().trim()));
        System.out.println(words[words.length - 1]);
        em.setSearchWord(searchWords);
        // Lưu vào db
        OfyService.ofy().save().entity(em).now();

        // Đổi từ entity sang dto
        EmployeeDTO dto = new EmployeeDTO();
        dto = entityToDto(em);

        return dto;
    }

    @Override
    public EmployeeDTO updateEm(EmployeeDTO employee, Long employeeId) throws EmployeeException {
        if (employee == null) {
            throw new EmployeeException("Dữ liệu nhân viên cập nhật không hợp lệ");
        }
        if (employeeId == null || employeeId <= 0) {
            throw new EmployeeException("ID nhân viên không hợp lệ");
        }

        Employee existingEmployee = OfyService.ofy().load().type(Employee.class)
                .id(employeeId).now();

        if (existingEmployee == null) {
            throw new EmployeeException("Không tìm thấy nhân viên với ID: " + employeeId);
        }

        if (employee.getFullName() != null && !employee.getFullName().trim().isEmpty()) {
            existingEmployee.setFullName(employee.getFullName().trim());
            String[] words = employee.getFullName().toLowerCase().trim().split(" "); // Tách theo khoảng trắng
            List<String> searchWords = new ArrayList<>();
            for (String string : words) {
                searchWords.add(removeVietnameseAccents(string));
            }
            existingEmployee.setSearchWord(searchWords);

            existingEmployee.setName(removeVietnameseAccents(words[words.length - 1].toLowerCase().trim()));
            System.out.println(words[words.length - 1]);
        }

        if (employee.getPhoneNumber() != null) {
            existingEmployee.setPhoneNumber(employee.getPhoneNumber().trim());
        }

        if (employee.getRole() != null && !employee.getRole().trim().isEmpty()) {
            existingEmployee.setRole(employee.getRole().trim());
        }

        OfyService.ofy().save().entity(existingEmployee).now();

        EmployeeDTO updatedDTO = new EmployeeDTO();
        updatedDTO = entityToDto(existingEmployee);

        return updatedDTO;
    }

    @Override
    public void deleteEm(Long employeeId) throws EmployeeException {
        //tìm kiếm xem nhân viên muốn cập nhật có đang tồn tại hay không
        Employee employee = OfyService.ofy().load().type(Employee.class)
                .id(employeeId).now();
        if (employee == null) {
            throw new EmployeeException("Nhân viên đang cần cập nhật không tồn tại");
        }
        // Xóa nhân viên được chọn khỏi danh sách
        OfyService.ofy().delete().entity(employee).now();
    }

    @Override
    public List<EmployeeDTO> getAll() throws EmployeeException {
        List<Employee> employees = OfyService.ofy().load().type(Employee.class).list();
        System.out.println("list employee :" + employees);
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO dto = new EmployeeDTO();
            dto = entityToDto(employee);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<EmployeeDTO> getListByRole(String keyword) throws EmployeeException {
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

    // Chuyển từ request sang entity
    private Employee requestToEntity(EmployeeRequest request) {
        Employee em = new Employee();
        em.setEmail(request.getEmail());
        em.setFullName(request.getFullName());
        em.setPassword(request.getPassword());
        em.setPhoneNumber(request.getPhoneNumber());
        em.setRole(request.getRole());
        return em;
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

    private String removeVietnameseAccents(String input) {
        if (input == null) return null;
    
        // Chuẩn hóa về dạng có dấu tách riêng (NFD)
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    
        // Xóa toàn bộ ký tự dấu (Mark)
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noAccent = pattern.matcher(normalized).replaceAll("");
    
        // Xử lý riêng Đ/đ
        noAccent = noAccent.replace('Đ', 'D').replace('đ', 'd');
    
        return noAccent;
    }

    @Override
    public List<EmployeeDTO> sortByName(String keyword) throws EmployeeException {
        List<EmployeeDTO> dtos = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        if (keyword.equals("Tăng dần")) {
            employees = OfyService.ofy().load().type(Employee.class).order("name").list();
        } else if (keyword.equals("Giảm dần")) {
            employees = OfyService.ofy().load().type(Employee.class).order("-name").list();
        } else {
            dtos = getAll();
        }
        if (employees != null || !employees.isEmpty()) {
            // Kiểm tra danh sách có rỗng không đã
            for (Employee employee : employees) {
                dtos.add(entityToDto(employee));
            }
        }
        return dtos;
    }

    @Override
    public List<EmployeeDTO> searchByEmail(String email) throws EmployeeException {
        List<Employee> employees = OfyService.ofy().load().type(Employee.class)
                .filter("email >=", email.toLowerCase().trim())
                .filter("email <", email.toLowerCase().trim() + "\uf8ff")
                .list();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            dtos.add(entityToDto(employee));
        }
        return dtos;
    }

    @Override
    public List<EmployeeDTO> searchByPhoneNumber(String phoneNumber) throws EmployeeException {
        List<Employee> employees = OfyService.ofy().load().type(Employee.class)
                .filter("phoneNumber >=", phoneNumber.toLowerCase().trim())
                .filter("phoneNumber <", phoneNumber.toLowerCase().trim() + "\uf8ff")
                .list();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            dtos.add(entityToDto(employee));
        }
        return dtos;
    }
}
