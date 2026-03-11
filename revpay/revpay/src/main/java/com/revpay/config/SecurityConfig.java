package com.revpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

import com.revpay.security.CustomUserDetailsService;

@Configuration
@Profile("!test")
public class SecurityConfig {
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService service,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(service);
        auth.setPasswordEncoder(passwordEncoder);

        return auth;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

        .csrf(csrf -> csrf.disable())

        .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/verify-otp",
                        "/verify-otp/**",

                        "/forgot-password",
                        "/verify-email",
                        "/verify-answer",
                        "/reset-password",

                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()
                .requestMatchers(
                        "/admin-dashboard",
                        "/admin/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        "/dashboard",
                        "/send-money",
                        "/add-money",
                        "/withdraw-money",
                        "/transactions-history",
                        "/profile",
                        "/payment-methods",
                        "/notifications"
                ).hasAnyRole("PERSONAL","BUSINESS","ADMIN")

                .anyRequest().authenticated()
        )

        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
        )

        .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }
}