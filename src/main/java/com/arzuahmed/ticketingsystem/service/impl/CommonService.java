package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.CommonServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;

@Service
@RequiredArgsConstructor
public class CommonService implements CommonServiceInterface {

    private final UserRepositoryInterface userRepository;

    @Override
    public User register(UserDTO userDTO) {
        User user = Mapper.userMapper(userDTO);
        return userRepository.save(user);
    }
}
