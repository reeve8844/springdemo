package com.example.springdemo.repository;

import com.example.springdemo.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {
    Employees findByName(String name);
    @Query(nativeQuery = true, value = "select * from Employees where is_delete = :bool")
    List<Employees> findByIs_delete(@Param("bool") boolean bool);
    List<Employees> findByNameLike(String name);
    @Query(nativeQuery = true, value = "select * from Employees where name like %:name% and is_delete = false")
    List<Employees> findByNameLikeIs_delete(@Param("name") String name);
}
