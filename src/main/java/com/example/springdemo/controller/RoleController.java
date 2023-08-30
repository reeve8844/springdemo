package com.example.springdemo.controller;

import com.example.springdemo.entity.Roles;
import com.example.springdemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/search-role")
    public ResponseEntity<List<Roles>> getRoles() {
        System.out.println("get Role: ");
        try {
            List<Roles> roles = new ArrayList<Roles>();
            roleRepository.findAll().forEach(roles::add);

            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/role", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Roles> createRoles(@ModelAttribute Roles roles) {
        System.out.println("create: "+roles.getName());
        if(roles.getName()==null){
            System.out.println("not blank");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else if (!roleRepository.findByName(roles.getName()).isEmpty()) {
            System.out.println("role exist");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            System.out.println("save data");
            Roles role = roleRepository.save(new Roles(roles.getName()));
            return new ResponseEntity<>(role, HttpStatus.CREATED);
        }

    }

    @PutMapping(value = "/role/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Roles> updateRoles(@PathVariable("id") Integer id, @ModelAttribute Roles roles) {
        System.out.println("update role: ");
        Optional<Roles> oldRole = roleRepository.findById(id);
        Date date = new Date();

        if (!oldRole.isEmpty()) {
            Roles newRole = oldRole.get();
            newRole.setName(roles.getName());
            newRole.setUpdated_at(date);
            return new ResponseEntity<>(roleRepository.save(newRole), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Roles> deleteRoles(@PathVariable("id") Integer id) {
        System.out.println("delete: ");
        try {
            roleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
