package com.example.demo1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Data
@Entity
@Table(name="Employees")
public class Employees {
    @Id
    @NonNull
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String password;
    private boolean is_dalete;
    private Date create_at;
    private Date update_at;
}
