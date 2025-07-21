package com.arzuahmed.ticketingsystem.controller;


import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserPasswordDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.response.TicketResponse;
import com.arzuahmed.ticketingsystem.service.impl.TicketService;
import com.arzuahmed.ticketingsystem.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;

    //buyTicket, register

    //User-in ticketlerini axtaris
   @GetMapping("/{userId}/tickets")
  public List<Ticket> getAllTickets(@PathVariable Long userId) {
        return userService.findAllTickets(userId);
    }


    //emailini deyisdirmek
    @PatchMapping("{userId}/email")
    public ResponseEntity<User> updateEmailInUser(@PathVariable Long userId, @RequestBody UserEmailDTO userEmailDTO){
        return userService.updateEmail(userId, userEmailDTO);
    }
//
//    //Password-nu deyisdirme
//    @PatchMapping("{userId}/password")
//    public ResponseEntity<User> updatePasswordInUser(@PathVariable Long userId, @RequestBody UserPasswordDTO userPasswordDTO){
//        return userService.updatePassword(userId, userPasswordDTO);
//    }
//
//
//
//    //register olmaq ucun
//   // @PostMapping("/register")
//   // @ResponseStatus(HttpStatus.CREATED)
//    //public ResponseEntity<User> register(@Valid @RequestBody UserDTO userDTO){
//       // return userService.register(userDTO);
//   // }
//
//    //Login
//
//
//    //Logout

    @PostMapping("/{userId}/tickets/buy")
    public ResponseEntity<TicketResponse> buyTicket(@PathVariable Long userId,
                                                    @RequestBody BuyTicketDTO buyTicketDTO){
      ticketService.buyTicket(userId, buyTicketDTO);
      return ResponseEntity.ok(new TicketResponse("Bilet ugurla alindi..", buyTicketDTO.getTicketNo()));
    }

}
