//package com.arzuahmed.ticketingsystem.config;
//
//import com.arzuahmed.ticketingsystem.security.jwt.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.net.http.HttpRequest;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthFilter;
//
//
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .sessionManagement(session->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth->
//                        auth.requestMatchers("/v1/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/v1/users/**").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/common/**", "/register", "/login").permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .addFilterAt( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}
