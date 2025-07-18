//package com.arzuahmed.ticketingsystem.security;
//
//import com.arzuahmed.ticketingsystem.model.entity.User;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//@Data
//@RequiredArgsConstructor
//public class UserSecurity implements UserDetails {
//
//    private final User user;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user.getRole().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
//                .toList();
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getEmail();
//    }
//}
