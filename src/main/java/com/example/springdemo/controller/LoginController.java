package com.example.springdemo.controller;

import com.example.springdemo.entity.Employees;
import com.example.springdemo.payload.LoginRequest;
import com.example.springdemo.repository.EmployeeRepository;
import com.example.springdemo.repository.RoleRepository;
import com.example.springdemo.security.jwt.JwtUtils;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller
@RestController
public class LoginController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

//    @PostMapping("/login")
//    public ResponseEntity<?> employeeLogin(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response){
//
//        Employees employees = employeeRepository.findByName(loginRequest.getName());
//
//        if (employees != null && employees.getPassword().equals(loginRequest.getPassword())) {
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword())
//            );
//
//            String token = jwtUtils.addJwtToResponse(authentication, response);
//
//            System.out.println(token);
//
//            //Optional<Roles> roles = roleRepository.findRoleByName(loginRequest.getName());
//
//            return ResponseEntity.ok().body(new LoginResponse(loginRequest.getName(), token));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response,
                        RedirectAttributes redirectAttributes){
        System.out.println("Request: "+loginRequest.getName()+" "+loginRequest.getPassword());
        Employees employees = employeeRepository.findByName(loginRequest.getName());

        System.out.println("employee: "+employees.getName()+" "+employees.getPassword());

        if(employees==null){
//            redirectAttributes.addFlashAttribute("errorMessage","User not exeist");
//            redirectAttributes.addFlashAttribute("name",loginRequest.getName());
//            redirectAttributes.addFlashAttribute("password",loginRequest.getPassword());
//            redirectAttributes.addFlashAttribute("fail",true);
//            return "redirect:/login";
        }else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword())
            );
            if(!authentication.isAuthenticated()){
//                redirectAttributes.addFlashAttribute("errorMessage", authentication.toString());
//                redirectAttributes.addFlashAttribute("name",loginRequest.getName());
//                redirectAttributes.addFlashAttribute("password",loginRequest.getPassword());
//                redirectAttributes.addFlashAttribute("fail",true);
//                return "redirect:/login";
            }else {
                String token = jwtUtils.addJwtToResponse(authentication, response);
//                return "redirect:/home";
            }
        }
        return "";
    }

}
