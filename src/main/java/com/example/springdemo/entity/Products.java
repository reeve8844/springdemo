package com.example.springdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Products")
public class Products {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id;
    @NonNull
    @Column(unique=true)
    private String name;
    private String description;
    private Integer price;
    private Integer stock_quantity;
    private String image_url;
    private Date created_at;
    private Date updated_at;
}
