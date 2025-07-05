package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.exception.UserAlreadyExistException;
import com.arzuahmed.ticketingsystem.model.User;
import com.arzuahmed.ticketingsystem.model.dto.UserDTO;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryInterface userRepository;

    public ResponseEntity<User> register(UserDTO userDTO) {
        User user = new User();
      if (userDTO.getId() != null && !userRepository.existsById(userDTO.getId())){
           user = new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(),
                   userDTO.getRole());
           userRepository.save(user);
           return ResponseEntity.ok(user);
      }
      throw new UserAlreadyExistException(userDTO.getName() + "  User is already existed");
    }


}
