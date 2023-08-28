package com.example.springdemo.details;

import com.example.springdemo.entity.Employees;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EmployeeDetails implements UserDetails {
    private final Employees employees;
    private Collection<? extends GrantedAuthority> authorities;

    public EmployeeDetails(Employees employee) {
        this.employees = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")); // You can customize roles here
    }

    @Override
    public String getPassword() {
        return employees.getPassword();
    }

    @Override
    public String getUsername() {
        return employees.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
