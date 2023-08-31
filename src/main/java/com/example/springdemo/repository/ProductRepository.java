package com.example.springdemo.repository;

import com.example.springdemo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    List<Products> findByNameLike(String name);

    Optional<Products> findByName(String name);
}
