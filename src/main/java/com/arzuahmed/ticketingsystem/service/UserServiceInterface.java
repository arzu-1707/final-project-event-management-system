package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserServiceInterface {
    Page<UserResponse> findAllUsers(Pageable pageable);
    UserResponse findUserById(Long userId);
    Page<UserResponse> findUserByUserName(String userName, Pageable pageable);
    UserResponse findUserByEmail(UserEmailDTO userEmailDTO);
    void deleteUser(Long userId);
    void save(User user);
//    //User addUserRole(Long userId, RoleDTO role);

}
