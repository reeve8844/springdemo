package com.example.springdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;

    public Categories() {
    }

    public Categories(String name) {
        this.name = name;
    }

    public Categories(String name, Integer parentId) {
        this.name = name;
        this.parent_id = parentId;
    }
}
