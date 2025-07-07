package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserPasswordDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryInterface userRepository;

    public ResponseEntity<User> register(UserDTO userDTO) {

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .password(userDTO.getPassword())
                .build();

        return ResponseEntity.ok(userRepository.save(user));
    }

    public List<User> findUserByUserName(String userName) {
       List<User> users = userRepository.findUserByName(userName);
       if (users.isEmpty()){
           throw new UserNotFound("User not found");
       }

       return users;
    }
    public User findUserByUserEmail(String userEmail) {
        return userRepository.findUserByEmail(userEmail).orElseThrow(()-> new UserNotFound("User is not found"));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public List<Ticket> findAllTickets(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        return user.getTickets();
    }

    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId))
        {
            throw new UserNotFound("User is not found");
        }
        userRepository.deleteById(userId);
    }

    public ResponseEntity<User> updateUser(Long userId, UserDTO userDTO) {
        User newUser = userRepository.findById(userId).orElseThrow(()->
                new UserNotFound("User is not found") );
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setRole(userDTO.getRole());
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    public ResponseEntity<User> updateEmail(Long userId, UserEmailDTO userEmailDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        user.setEmail(userEmailDTO.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }


    public ResponseEntity<User> updatePassword(Long userId, UserPasswordDTO userPasswordDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        user.setPassword(userPasswordDTO.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }


}
