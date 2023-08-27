package com.example.springdemo.service;

import com.example.springdemo.details.EmployeeDetails;
import com.example.springdemo.entity.Employees;
import com.example.springdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employees employees = employeeRepository.findByName(username);

        if (employees == null) {
            throw new UsernameNotFoundException("Employee not found with name: " + username);
        }

        EmployeeDetails employeeDetails = new EmployeeDetails(employees);
        //employeeDetails.getAuthorities();


        return employeeDetails; // Assuming you have implemented EmployeeDetails class
    }
}
