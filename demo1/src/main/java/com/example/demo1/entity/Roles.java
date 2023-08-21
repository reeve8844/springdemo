package com.example.demo1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Roles")
public class Roles {
    @Id
    @NonNull
    private int id;
    @NonNull
    private String name;
    private Date create_at;
    private Date update_at;
}
