package com.example.springdemo.dto;

import com.example.springdemo.entity.Employees;
import com.example.springdemo.entity.Roles;

public class EmployeeWithRoleDTO {
    private Employees employee;
    private Roles role;

    public EmployeeWithRoleDTO(Employees employee, Roles role) {
        this.employee = employee;
        this.role = role;
    }

    public EmployeeWithRoleDTO() {

    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
