package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.userExceptions.InvalidOldPasswordException;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UsersNotFound;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserPasswordDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.UserServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> findAllUsers(Pageable pageable) {
        Page<User> allUsers = userRepository.findAll(pageable);
        if (allUsers.isEmpty()){
            throw new UsersNotFound(ErrorCode.USERS_NOT_FOUND);
        }
        return Mapper.userResponseMapper(allUsers);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));
       return Mapper.userResponseMapper(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> findUserByUserName(String userName, Pageable pageable) {
        Page<User> users = userRepository.findAllByUserName(userName, pageable);
        if (users.isEmpty()){
            throw new UsersNotFound(ErrorCode.USERS_NOT_FOUND);
        }
        return Mapper.userResponseMapper(users);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse findUserResponseByEmail(UserEmailDTO userEmailDTO) {
        User user = userRepository.findUserByEmail(userEmailDTO.getEmail()).stream()
                .findFirst().orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));
     return Mapper.userResponseMapper(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFound(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
        log.info("User is deleted. User Id: {}, User Name: {}", user.getId(), user.getUserName());
   }

   public User findById(Long userId){
       return userRepository.findById(userId).orElseThrow(()-> new UserNotFound(ErrorCode.USER_NOT_FOUND));
   }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<Ticket> findAllTickets(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));
        return user.getTickets();
    }
//
//
//   /* public ResponseEntity<User> updateUser(Long userId, UserDTO userDTO) {
//        User newUser = userRepository.findById(userId).orElseThrow(()->
//                new UserNotFound("User is not found") );
//        newUser.setName(userDTO.getName());
//        newUser.setEmail(userDTO.getEmail());
//        newUser.setPassword(userDTO.getPassword());
//        newUser.setRole(userDTO.getRole());
//        userRepository.save(newUser);
//        return ResponseEntity.ok(newUser);
//    } */
//
    public UserResponse updateEmail(Long userId, UserEmailDTO userEmailDTO) {
        User user = findById(userId);
        user.setEmail(userEmailDTO.getEmail());
        User savedUser = userRepository.save(user);
        return Mapper.userResponseMapper(savedUser);
   }


    public UserResponse updatePassword(Long userId, UserPasswordDTO userPasswordDTO) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserNotFound(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(userPasswordDTO.getOldPassword(), user.getPassword())){
            throw new InvalidOldPasswordException(ErrorCode.USER_INVALID_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(userPasswordDTO.getNewPassword()));
        User savedUser = userRepository.save(user);
      return   Mapper.userResponseMapper(savedUser);
    }

    //Security ucun metod
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

}
