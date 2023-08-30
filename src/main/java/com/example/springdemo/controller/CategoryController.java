package com.example.springdemo.controller;

import com.example.springdemo.dto.EmployeeWithRoleDTO;
import com.example.springdemo.entity.Categories;
import com.example.springdemo.entity.Employees;
import com.example.springdemo.entity.Roles;
import com.example.springdemo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/search-category")
    public ResponseEntity<List<Categories>> getCategoryByName(@RequestParam(required = false) String name) {
        System.out.println("get Category: ");
        try {
            List<Categories> categories = new ArrayList<>();

            if (name.isEmpty()) {
                categoryRepository.findAll().forEach(categories::add);
            } else {
                categoryRepository.findByNameLike(name).forEach(categories::add);
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

//    @PutMapping(value = "/role/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Roles> updateRoles(@PathVariable("id") Integer id, @ModelAttribute Roles roles) {
//        System.out.println("update role: ");
//        Optional<Roles> oldRole = roleRepository.findById(id);
//        Date date = new Date();
//
//        if (!oldRole.isEmpty()) {
//            Roles newRole = oldRole.get();
//            newRole.setName(roles.getName());
//            newRole.setUpdated_at(date);
//            return new ResponseEntity<>(roleRepository.save(newRole), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/role/{id}")
//    public ResponseEntity<Roles> deleteRoles(@PathVariable("id") Integer id) {
//        System.out.println("delete: ");
//        try {
//            roleRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
}
