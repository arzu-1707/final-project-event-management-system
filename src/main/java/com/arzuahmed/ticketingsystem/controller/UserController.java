package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.User;
import com.arzuahmed.ticketingsystem.model.dto.UserDTO;
import com.arzuahmed.ticketingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }

   /* @GetMapping("/userName/{userName}")
    public User findUserByUserName(@PathVariable String userName){
        return userService.findUserByUserName(userName);
    }*/


}
