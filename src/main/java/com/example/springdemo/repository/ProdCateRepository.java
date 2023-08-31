package com.example.springdemo.repository;

import com.example.springdemo.entity.Product_Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdCateRepository extends JpaRepository<Product_Categories, Integer> {
    @Query(nativeQuery = true, value = "select * from Product_Categories where product_id = :id")
    Optional<Product_Categories> findByProductId(@Param("id") Integer id);
}
