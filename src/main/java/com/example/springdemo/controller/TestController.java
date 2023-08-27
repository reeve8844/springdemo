package com.example.springdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class TestController {
    @RequestMapping("/123")
    public String hello(){
        return "Hello Spring";
    }
}
