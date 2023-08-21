package com.example.demo1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Products")
public class Products {
    @Id
    @NonNull
    private int product_id;
    @NonNull
    private String name;
    private String description;
    private int price;
    private int stock_quantity;
    private String image_url;
    private Date create_at;
    private Date update_at;
}
