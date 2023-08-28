package com.example.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootController {
    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/employee")
    public String employee() {
        return "employee";
    }

//    @RequestMapping("/login")
//    public String login(@RequestParam(value="name", required=false) String name,
//                        @RequestParam(value="password", required=false) String password,
//                        @RequestParam(value="errorMessage", required=false) String errorMessage,
//                        @RequestParam(value="fail", required=false) boolean fail,
//                        Model model) {
//        if (fail) {
//            model.addAttribute("name", name);
//            model.addAttribute("password", password);
//            model.addAttribute("errorMessage", errorMessage);
//            model.addAttribute("fail",fail);
//            return "redirect:/login";
//        }
//        else return "login";
//    }


}
