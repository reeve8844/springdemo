package com.example.springdemo.controller;

import com.example.springdemo.entity.Employees;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
    @RequestMapping("/")
    public String home() {
        return "home";
    }

}
