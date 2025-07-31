package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserLoginRequest;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserTokenResponse;

public interface CommonServiceInterface {

    UserResponse register(UserDTO userDTO);

    UserTokenResponse login(UserLoginRequest userLoginRequest);
}
