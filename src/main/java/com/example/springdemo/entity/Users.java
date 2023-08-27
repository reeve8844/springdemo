package com.example.springdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.sql.Time;

@Data
@Entity
@Table(name="Users")
public class Users implements Serializable {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(unique=true)
    private String name;
    private String email;
    @NonNull
    private String password;
    private Time created_at;
    private Time updated_at;

    public Users() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Time getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Time create_at) {
        this.created_at = create_at;
    }

    public Time getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Time update_at) {
        this.updated_at = update_at;
    }
}
