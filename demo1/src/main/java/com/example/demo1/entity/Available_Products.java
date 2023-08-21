package com.example.demo1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Available_Products")
public class Available_Products {
    @Id
    @NonNull
    private int available_product_id;
    @NonNull
    private String name;
    private int price;
    private Date create_at;
    private Date update_at;
}
