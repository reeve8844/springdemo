package com.example.springdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Categories")
public class Categories {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer category_id;
    private Integer parent_id;
    @NonNull
    @Column(unique=true)
    private String name;
    private Date created_at;
    private Date updated_at;
}
