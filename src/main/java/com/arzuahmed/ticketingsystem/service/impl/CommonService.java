package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.userExceptions.UserAlreadyExistException;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserLoginRequest;
import com.arzuahmed.ticketingsystem.model.entity.Role;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserTokenResponse;
import com.arzuahmed.ticketingsystem.repository.RoleRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import com.arzuahmed.ticketingsystem.security.jwt.JwtService;
import com.arzuahmed.ticketingsystem.service.CommonServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService implements CommonServiceInterface {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RoleRepositoryInterface roleRepositoryInterface;


    @Override
    public UserResponse register(UserDTO userDTO) {

        userService.findByEmail(userDTO.getEmail()).ifPresent(user1->
         {
             throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXITS_EXCEPTION);
         });

        User user = Mapper.userMapper(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (user.getRoles()==null || user.getRoles().isEmpty()){
            Role role = roleRepositoryInterface.findByRole("USER");
            if (role == null){
                role = new Role();
                role.setRole("USER");
                role = roleRepositoryInterface.save(role);
            }
            user.getRoles().add(role);
        }
        User savedUser = userService.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        log.info("User is registered. User Id: {}, User Name: {}", user.getId(), user.getUserName());
        UserResponse userResponse = Mapper.userResponseMapper(savedUser);
        userResponse.setAccessToken(accessToken);
        userResponse.setRefreshToken(refreshToken);
        return userResponse;
    }

    @Override
    public UserTokenResponse login(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return UserTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();



    }
}
