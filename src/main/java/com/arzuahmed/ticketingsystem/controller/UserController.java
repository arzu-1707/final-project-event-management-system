package com.arzuahmed.ticketingsystem.controller;


import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserPasswordDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    //UserName-e gore axtaris
    @GetMapping("/search")
    public List<User> findUserByUserName(@RequestParam String name){

        return userService.findUserByUserName(name);
    }

    //butun userleri axtaris
    @GetMapping
    public List<User> findAllUsers() {

        return userService.findAllUsers();
    }

    //Email-e gore axtaris
    @GetMapping("/userEmail/{userEmail}")
    public User findUserByUserEmail(@PathVariable String userEmail) {
        return userService.findUserByUserEmail(userEmail);
    }

    //User-in ticketlerini axtaris
    @GetMapping("/{userId}/tickets")
    public List<Ticket> getAllTickets(@PathVariable Long userId) {
        return userService.findAllTickets(userId);
    }

    //UserId-e aid olan User-i silmek
    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok("USER is deleted");
    }

    //butun obyektlerinde deyisiklik aparmaq
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    //emailini deyisdirmek
    @PatchMapping("{userId}/email")
    public ResponseEntity<User> updateEmailInUser(@PathVariable Long userId, @RequestBody UserEmailDTO userEmailDTO){
        return userService.updateEmail(userId, userEmailDTO);
    }

    //Password-nu deyisdirme
    @PatchMapping("{userId}/password")
    public ResponseEntity<User> updatePasswordInUser(@PathVariable Long userId, @RequestBody UserPasswordDTO userPasswordDTO){
        return userService.updatePassword(userId, userPasswordDTO);
    }

    //regsiter olmaq ucun
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }

    //Login


    //Logout


}
