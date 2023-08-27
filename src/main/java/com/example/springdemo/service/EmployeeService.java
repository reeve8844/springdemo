package com.example.springdemo.service;

import com.example.springdemo.entity.Employees;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
//    String CreateEmployee(Employees employees);
//    List<Employees> LookupByName(String username);
//    List<Employees> Login(String username,String password);
//    String UpdateById(int id,Employees employees);
    public List<Employees> getAllEmployees();
    public Optional<Employees> getEmployee(String name);
}
