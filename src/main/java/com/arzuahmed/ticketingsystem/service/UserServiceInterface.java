package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserInfoResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserServiceInterface {
    Page<UserInfoResponse> findAllUsers(Pageable pageable);
    UserResponse findUserById(Long userId);
    Page<UserResponse> findUserByUserName(String userName, Pageable pageable);
    UserResponse findUserResponseByEmail(UserEmailDTO userEmailDTO);
    void deleteUser(Long userId);
    User save(User user);

    void deleteAllUsers();
//    //User addUserRole(Long userId, RoleDTO role);

}
