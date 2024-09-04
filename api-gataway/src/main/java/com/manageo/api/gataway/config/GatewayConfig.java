/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manageo.api.gataway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 *
 * @author wassim
 */
@Configuration
public class GatewayConfig {
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails user1 = User.builder()
                .username("pooja")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, user1);
    }
    
     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("api-authentication", r -> r
                .path("/auth/**")
                .uri("lb://API-AUTHENTICATION")
            )
            .route("reservation-service", r -> r
                .path("/reservations/**")
                .filters(f -> f
                    .rewritePath("/reservations/(?<segment>.*)", "/${segment}")
                    .filter(new JwtHeaderFilter())  // Ensure JwtHeaderFilter is properly instantiated
                )
                .uri("lb://RESERVATION-SERVICE")
            )
            .route("restaurant-service", r -> r
                .path("/restaurants/**")
                .filters(f -> f
                    .rewritePath("/restaurants/(?<segment>.*)", "/${segment}")
                    .filter(new JwtHeaderFilter())  // Ensure JwtHeaderFilter is properly instantiated
                )
                .uri("lb://RESTAURANT-SERVICE")
            )
            .build();
    }
}