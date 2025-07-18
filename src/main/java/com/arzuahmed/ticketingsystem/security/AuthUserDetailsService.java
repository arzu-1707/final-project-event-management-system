//package com.arzuahmed.ticketingsystem.security;
//
//import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
//import com.arzuahmed.ticketingsystem.model.entity.User;
//import com.arzuahmed.ticketingsystem.service.impl.UserService;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Data
//@Service
//@RequiredArgsConstructor
//public class AuthUserDetailsService implements UserDetailsService {
//
//    private final UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
//        return new  UserSecurity(user);
//    }
//}
