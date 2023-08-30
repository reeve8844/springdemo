package com.example.springdemo.repository;

import com.example.springdemo.entity.Employee_Roles;
import com.example.springdemo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRolesRepository extends JpaRepository<Employee_Roles, Integer> {
    @Query(nativeQuery = true, value = "SELECT r.id, r.name, r.created_at, r.updated_at " +
            "FROM Roles r " +
            "JOIN Employee_Roles er ON r.id = er.role_id " +
            "JOIN Employees e ON e.id = er.employee_id " +
            "WHERE e.name = :name")
    Roles findRolesByName(@Param("name") String name);
    @Query(nativeQuery = true, value = "select * from Employee_Roles where employee_id = :id")
    Optional<Employee_Roles> findByEmployeeId(@Param("id") Integer id);
}
