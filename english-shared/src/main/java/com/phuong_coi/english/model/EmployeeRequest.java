package com.phuong_coi.english.model;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EmployeeRequest implements Serializable, IsSerializable{
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private String role;
    private String fullNameToSearch;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String fullName, String phoneNumber, String email, String password, String role) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setFullNameToSearch(String fullNameToSearch){
        this.fullNameToSearch = fullNameToSearch;
    }
}
