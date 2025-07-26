package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UsersNotFound;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserPasswordDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.UserServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()){
            throw new UsersNotFound(ErrorCode.USERS_NOT_FOUND);
        }
        return allUsers;
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new UserNotFound(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public List<User> findUserByUserName(String userName) {
        List<User> users = userRepository.findUserByUserName(userName);
        if (users.isEmpty()){
            throw new UsersNotFound(ErrorCode.USERS_NOT_FOUND);
        }
        return users;
    }

    @Override
    public User findUserByEmail(UserEmailDTO userEmailDTO) {
        return userRepository.findUserByEmail(userEmailDTO.getEmail()).stream()
                .findFirst().orElseThrow(()-> new UserNotFound(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public void deleteUser(Long userId) {
      User user = findUserById(userId);
        userRepository.delete(user);
   }


//   // public ResponseEntity<User> register(UserDTO userDTO) {
//
//      /*  User user = User.builder()
//                .name(userDTO.getName())
//                .email(userDTO.getEmail())
//                .role(userDTO.getRole())
//                .password(userDTO.getPassword())
//                .build();
//
//        return ResponseEntity.ok(userRepository.save(user));
//        */
//   // }
//
//
    @Transactional
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
    public ResponseEntity<User> updateEmail(Long userId, UserEmailDTO userEmailDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));
        user.setEmail(userEmailDTO.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(user);
   }
//
//
//    public ResponseEntity<User> updatePassword(Long userId, UserPasswordDTO userPasswordDTO) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
//        user.setPassword(userPasswordDTO.getPassword());
//        userRepository.save(user);
//        return ResponseEntity.ok(user);
//    }
//
//
//    //public Page<Event> getAllEvents(Pageable pageable) {
//      //  return userRepository.get
//
//    //}

    //Security ucun metod
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user){
        userRepository.save(user);
    }
}
