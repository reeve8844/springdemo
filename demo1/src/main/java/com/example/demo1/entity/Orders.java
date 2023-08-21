package com.example.demo1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Data
@Table(name="Orders")
public class Orders {
    @Id
    @NonNull
    private int order_id;
    @NonNull
    private int user_id;
    private int total_amount;
    @NonNull
    private int order_status;
    @NonNull
    private int payment_status;
    private Date create_at;
    private Date update_at;
}
