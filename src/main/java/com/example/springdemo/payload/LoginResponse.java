package com.example.springdemo.payload;

import jakarta.validation.constraints.NotBlank;

public class LoginResponse {
    private String name;
    private String accessToken;

    public LoginResponse() {
    }
    public LoginResponse(String name, String accessToken) {
        this.name = name;
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getaccessToken() {
        return accessToken;
    }

    public void setaccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
