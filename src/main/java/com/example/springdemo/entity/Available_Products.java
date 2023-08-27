package com.example.springdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Available_Products")
public class Available_Products {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer available_product_id;
    @NonNull
    private String name;
    @NonNull
    private Integer price;
    private Date created_at;
    private Date updated_at;
}
