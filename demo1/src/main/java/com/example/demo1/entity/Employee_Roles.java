package com.example.demo1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Employee_Roles")
public class Employee_Roles {
    @Id
    @NonNull
    private int id;
    @NonNull
    private int employee_id;
    @NonNull
    private int role_id;
    private Date create_at;
    private Date update_at;
}