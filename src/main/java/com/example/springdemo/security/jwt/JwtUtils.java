package com.example.springdemo.security.jwt;

import java.util.Date;

import com.example.springdemo.details.EmployeeDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtUtils {
    private static final long expireTime = 60 * 60 * 1000; //1hr
    private static String secretKey = "SecretKey1234567890abcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcba";

    public static String addJwtToResponse(Authentication authentication, HttpServletResponse response) {
        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();

        String token = Jwts.builder()
                .setSubject(employeeDetails.getUsername())
                .claim("authorities", employeeDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);

        return token;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired");
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace");
        } catch (MalformedJwtException ex) {
            System.out.println("JWT is invalid");
        } catch (UnsupportedJwtException ex) {
            System.out.println(ex);
        } catch (SignatureException ex) {
            System.out.println("Signature validation failed");
        }

        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}