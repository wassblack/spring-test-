package com.manageo.api.authentication.config;

import com.manageo.api.authentication.security.JwtAuthenticationEntryPoint;
import com.manageo.api.authentication.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfigImpl {

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disabling CSRF protection (review necessity)
                .cors(cors -> cors.disable()) // Disabling CORS (review necessity)
                .exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint)) // Custom entry point
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll() // Public endpoint
                .anyRequest().authenticated()) // All other requests require authentication
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // JWT filter

        return http.build();
    }

}
