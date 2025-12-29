package com.phuong_coi.english.model;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EmployeeDTO implements Serializable, IsSerializable{
    private Long employeeId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String role;

    // Constructor không tham số
    public EmployeeDTO() {
    }

    // Constructor đầy đủ tham số
    public EmployeeDTO(Long employeeId, String fullName, String phoneNumber, String email, String role) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
