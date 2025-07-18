package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.entity.User;

import java.util.List;

public interface UserServiceInterface {
   List<User> findAllUsers();
   User findUserById(Long userId);
   List<User> findUserByUserName(String userName);
    User findUserByEmail(UserEmailDTO userEmailDTO);
    void deleteUser(Long userId);
//    //User addUserRole(Long userId, RoleDTO role);

}
