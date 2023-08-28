package com.example.springdemo.security.jwt;

import com.example.springdemo.service.TokenBlacklistService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklist implements TokenBlacklistService {
    private Set<String> blacklistedTokens = new HashSet<>();

    @Override
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
