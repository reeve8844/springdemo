package com.example.springdemo.config;

import com.example.springdemo.details.EmployeeDetails;
import com.example.springdemo.repository.RoleRepository;
import com.example.springdemo.security.jwt.AuthEntryPointJwt;
import com.example.springdemo.security.jwt.JwtTokenFilter;
import com.example.springdemo.service.EmployeeDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    @Autowired
//    RoleRepository roleRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private EmployeeDetailsService employeeDetailsService;
//    @Autowired
//    private DataSource dataSource;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    AuthEntryPointJwt authEntryPointJwt;


//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                //.setType(EmbeddedDatabaseType.H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }

//    @Bean
//    public UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        return users;
//    }


    @Bean
    @Scope("prototype")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .formLogin(form -> form.loginPage("/login").permitAll()
//                        .successHandler(jsonAuthenticationSuccessHandler())
//                        .failureHandler(jsonAuthenticationFailureHandler()))
//                .logout(logout -> logout.logoutUrl("/"))
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(authEntryPointJwt))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        //.requestMatchers("/**").hasAnyAuthority("ROLE_USER")
                        .anyRequest().authenticated())
                //.authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        System.out.println("fail handle");
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            val objectMapper = new ObjectMapper();
            val data = Map.of("title","login fail","status","error","message",exception.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().println(objectMapper.writeValueAsString(data));
        };
    }

    private AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        System.out.println("success handle");
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());
            val objectMapper = new ObjectMapper();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().println(objectMapper.writeValueAsString(authentication));
        };
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String name = authentication.getPrincipal().toString();
                String password = authentication.getCredentials().toString();

                // Load details from MariaDB
                EmployeeDetails employeeDetails = employeeDetailsService.loadUserByUsername(name);
//                System.out.println(employeeDetails.getUsername());
//                System.out.println(employeeDetails.getPassword());
//                System.out.println(employeeDetails.getAuthorities());
                String hashPasswd = passwordEncoder.encode(employeeDetails.getPassword());

                // Compare entered password with the encoded password from database
                if (passwordEncoder.matches(password, hashPasswd)) {
                    System.out.println("password true");
                    return new UsernamePasswordAuthenticationToken(employeeDetails, password, employeeDetails.getAuthorities());
                } else {
                    throw new BadCredentialsException("Authentication failed");
                }
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

}