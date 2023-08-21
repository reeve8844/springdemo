package com.example.demo1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Product_Categories")
public class Product_Categories {
    @Id
    @NonNull
    private int product_category_id;
    @NonNull
    private int product_id;
    @NonNull
    private int category_id;
    private Date create_at;
    private Date update_at;
}
