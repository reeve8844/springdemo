package com.example.springdemo.dto;

import com.example.springdemo.entity.Categories;
import com.example.springdemo.entity.Products;

public class ProductWithCategoryDTO {
    private Products products;
    private Categories categories;

    public ProductWithCategoryDTO() {
    }

    public ProductWithCategoryDTO(Products products, Categories categories) {
        this.products = products;
        this.categories = categories;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
