package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.userExceptions.UserAlreadyExistException;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.CommonServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CommonService implements CommonServiceInterface {

    private final UserService userService;

    @Override
    public UserResponse register(UserDTO userDTO) {

         userService.findByEmail(userDTO.getEmail()).ifPresent(user1->
         {
             throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXITS_EXCEPTION);
         });
        User user = Mapper.userMapper(userDTO);
        User savedUser = userService.save(user);
        return Mapper.userResponseMapper(savedUser);
    }
}
