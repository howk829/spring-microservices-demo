package com.example.microservices.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").authenticated() // Secure Actuator endpoints
                        .anyRequest().permitAll() // Allow public access to all other endpoints
                )
                .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Authentication
        return http.build();
    }


}
