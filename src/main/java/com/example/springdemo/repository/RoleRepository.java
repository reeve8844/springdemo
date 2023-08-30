package com.example.springdemo.repository;

import com.example.springdemo.entity.Employee_Roles;
import com.example.springdemo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findById(Roles role_id);

    @Query(nativeQuery = true, value = "select Roles.id, Roles.name, Roles.created_at, Roles.updated_at from Roles " +
            "join Employee_Roles on Roles.id = Employee_Roles.role_id " +
            "join Employees on Employee_Roles.employee_id = Employees.id " +
            "where Employees.name = :name or :name is null or :name = '' ")
    Optional<Roles>findByName(@Param("name") String name);
    Optional<Roles>findById(Integer id);
}
