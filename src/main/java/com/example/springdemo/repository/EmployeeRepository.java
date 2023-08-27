package com.example.springdemo.repository;

import com.example.springdemo.entity.Employees;
import com.example.springdemo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {
//    Optional<Employees> findByName(String name);
//
//    Boolean existsByName(String name);
    //Optional<Employees>findByName(String name);
    Employees findByName(String name);
}
