package com.example.springdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="Employees", uniqueConstraints = { @UniqueConstraint(columnNames = "name")})
public class Employees implements Serializable {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @NonNull
    @Column(unique=true)
    private String name;
    @NonNull
    private String password;
    @Value("false")
    private boolean is_delete;
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;

    public Employees() {

    }

    public Employees(String name, String password, boolean b) {
        this.name = name;
        this.password = password;
        this.is_delete = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
