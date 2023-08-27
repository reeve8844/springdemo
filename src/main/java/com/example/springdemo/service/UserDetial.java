package com.example.springdemo.service;

import com.example.springdemo.entity.Users;
//import com.example.demo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//public class UserDetial implements UserDetailsService {
//    @Autowired
//    UserRepository userRepo;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if(user==null) {
//            new UsernameNotFoundException("User not exists.");
//        }
//        return new org.springframework.security.core.userdetails.User(username,user.getPassword());
//    }
//}
