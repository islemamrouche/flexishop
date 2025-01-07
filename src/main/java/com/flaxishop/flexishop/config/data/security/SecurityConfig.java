package com.flaxishop.flexishop.config.data.security;

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Keycloak security configuration
        http
                .csrf().disable() // Disable CSRF for stateless APIs
                .authorizeRequests()
                .requestMatchers("/api/**").authenticated()  // Protect API endpoints
                .anyRequest().permitAll();  // Allow other requests

        return http.build();
    }

    @Bean
    public KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
        return new KeycloakAuthenticationProvider();
    }
}
