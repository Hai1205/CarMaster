/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class EmployeeDTO {
    private String employeeID, permissionID, employeeName, gender, phone, email, password, status;
    private Timestamp hiredate;
    private Date DOB;
    private int salary, OTP;

    public void setPermissionID(String permissionID) {
        this.permissionID = permissionID;
    }

    public void setHiredate(Timestamp hiredate) {
        this.hiredate = hiredate;
    }

    public String getPermissionID() {
        return permissionID;
    }

    public Timestamp getHiredate() {
        return hiredate;
    }
    
    public EmployeeDTO() {
    }

    public EmployeeDTO(String employeeID, String permissionID, String employeeName, Timestamp hiredate, String gender, String phone, String email, String password, String status, Date DOB, int salary, int OTP) {
        this.employeeID = employeeID;
        this.permissionID = permissionID;
        this.employeeName = employeeName;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.phone = phone;
        this.hiredate = hiredate;
        this.salary = salary;
        this.status = status;
        this.password = password;
        this.OTP = OTP;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }
    
    public void setHireDate(Timestamp hiredate) {
        this.hiredate = hiredate;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setOTP(int OTP) {
        this.OTP = OTP;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public Date getDOB() {
        return DOB;
    }

    public Timestamp getHireDate() {
        return hiredate;
    }

    public int getSalary() {
        return salary;
    }

    public int getOTP() {
        return OTP;
    }
}
