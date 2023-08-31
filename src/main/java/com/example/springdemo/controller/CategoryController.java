package com.example.springdemo.controller;

import com.example.springdemo.entity.Categories;
import com.example.springdemo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/category-list")
    public ResponseEntity<List<Categories>> getCategoryList() {
        System.out.println("get Category List: ");
        try {
            List<Categories> categories = new ArrayList<>();

            categoryRepository.findAll().forEach(categories::add);

            if (categories.isEmpty()) {
                System.out.println("Category not found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search-category")
    public ResponseEntity<List<Categories>> getCategoryByName(@RequestParam(required = false) String name) {
        System.out.println("get Category: ");
        try {
            List<Categories> categories = new ArrayList<>();

            if (name.isEmpty()) {
                categoryRepository.findAll().forEach(categories::add);
            } else {
                categoryRepository.findByNameLike("%"+name+"%").forEach(categories::add);
            }

            if (categories.isEmpty()) {
                System.out.println("Category not found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Categories> createCategory(@ModelAttribute Categories categories) {
        System.out.println("Create category: "+categories.getName());
        if(categories.getName().isEmpty()){
            System.out.println("not blank");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else if (categoryRepository.findByName(categories.getName()).isPresent()) {
            System.out.println("category exist");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            System.out.println("save data");
            categoryRepository.save(categories);
            return new ResponseEntity<>(categories, HttpStatus.CREATED);

        }

    }

    @PutMapping(value = "/category/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Categories> updateCategory(@PathVariable("id") Integer id, @ModelAttribute Categories categories) {
        System.out.println("update category: ");
        Optional<Categories> opCategory = categoryRepository.findById(id);
        Date date = new Date();

        Categories category = opCategory.get();
        category.setName(categories.getName());

        if (Optional.ofNullable(categories.getParent_id()).isPresent()) {
            category.setParent_id(categories.getParent_id());
        }

        return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Categories> deleteRoles(@PathVariable("id") Integer id) {
        System.out.println("delete: ");
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
