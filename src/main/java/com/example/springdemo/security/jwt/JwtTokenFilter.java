package com.example.springdemo.security.jwt;

import java.io.IOException;

import com.example.springdemo.details.EmployeeDetails;
import com.example.springdemo.service.EmployeeDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    EmployeeDetailsService employeeDetailsService;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //System.out.println("getHeader: "+request.getHeader("Authorization"));
        // Check if the request has a valid Authorization header
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            //System.out.println("!hasAuthorizationBearer");
            return;
        }

        // Get the token from the request
        String token = getAccessToken(request);
        System.out.println("getRequestToken: "+token);

        // Validate the token
        if (!jwtUtils.validateToken(token)) {
            filterChain.doFilter(request, response);
            //System.out.println("!jwtUtil.validateToken");
            return;
        }

        if(tokenBlacklist.isBlacklisted(token)){
            filterChain.doFilter(request, response);
            System.out.println("token in blacklist");
        }

        // Set the Authentication context if the token is valid
        setAuthenticationContext(token, request);

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ");
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header != null ? header.replace("Bearer ", "") : null;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        System.out.println("set auth context");
        String name = jwtUtils.getSubject(token);
        System.out.println("subject: "+name);
        if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            EmployeeDetails employeeDetails = employeeDetailsService.loadUserByUsername(name);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(employeeDetails, null, employeeDetails.getAuthorities());

            authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}