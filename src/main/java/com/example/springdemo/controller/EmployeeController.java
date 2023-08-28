package com.example.springdemo.controller;

import com.example.springdemo.entity.Employees;
import com.example.springdemo.repository.EmployeeRepository;
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
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/search-employee")
    public ResponseEntity<List<Employees>> getEmployeesByName(@RequestParam(required = false) String name) {
        System.out.println("get Employee: ");
        try {
            List<Employees> employees = new ArrayList<Employees>();

            if (name == null)
                employeeRepository.findByIs_delete(false).forEach(employees::add);
            else
                employeeRepository.findByNameLikeIs_delete(name).forEach(employees::add);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employees> createEmployees(@ModelAttribute Employees employee) {
        System.out.println("create: "+employee.getName()+" "+employee.getPassword());
        if(employee.getName()==null||employee.getPassword()==null){
            System.out.println("not blank");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else if (employeeRepository.findByName(employee.getName())!=null) {
            System.out.println("employee exist");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            System.out.println("save data");
            Employees employees = employeeRepository.save(new Employees(employee.getName(), employee.getPassword(), false));
            System.out.println("save data2");
            return new ResponseEntity<>(employees, HttpStatus.CREATED);
//            try {
//                Employees employees = employeeRepository
//                        .save(new Employees(employee.getName(), employee.getPassword(), false));
//                return new ResponseEntity<>(employees, HttpStatus.CREATED);
//            } catch (Exception e) {
//                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
        }

    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employees> updateEmployees(@PathVariable("id") Integer id, @RequestBody Employees Employees) {
        System.out.println("update: ");
        Optional<Employees> employees = employeeRepository.findById(id);
        Date date = new Date();

        if (employees.isPresent()) {
            Employees employee = employees.get();
            employee.setName(Employees.getName());
            employee.setPassword(Employees.getPassword());
            employee.setUpdated_at(date);
            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employees> deleteEmployees(@PathVariable("id") Integer id, @RequestBody Employees Employees) {
        System.out.println("delete: ");
        Optional<Employees> employees = employeeRepository.findById(id);

        if (employees.isPresent()) {
            Employees employee = employees.get();
            employee.setIs_delete(true);
            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        try {
//            employeeRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }


}
