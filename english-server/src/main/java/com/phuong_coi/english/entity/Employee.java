package com.phuong_coi.english.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity(name = "employees")
public class Employee {
    @Id
    private Long employeeId;
    @Index
    private String fullName;
    @Index
    private String email;
    @Index
    private String phoneNumber;
    @Index
    private String password;
    @Index
    private String role;

    public Employee(){}

    public Employee(Long employeeId, String fullName, String email, String phoneNumber, String password, String role){
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    //getter và setter
    public Long getId(){
        return employeeId;
    }

    public String getFullName(){
        return fullName;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getRole(){
        return role;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    // Getter cho password (chỉ trả về chuỗi hash)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(String role){
        this.role = role;
    }
}
