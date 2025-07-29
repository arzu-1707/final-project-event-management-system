package com.arzuahmed.ticketingsystem.controller;


import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserPasswordDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.response.CommonResponse;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponse;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketsResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.service.impl.TicketService;
import com.arzuahmed.ticketingsystem.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    //User-in ticketlerini axtaris +++POSTMAN++
   @GetMapping("/{userId}/tickets")
  public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> getAllTickets(
          @PathVariable Long userId) {

       List<TicketResponseDTO> tickets = ticketService.findAllTickets(userId);
       return ResponseEntity.status(HttpStatus.OK)
               .body(CommonResponse.success("Ugurla yerine yetirildi...",tickets ));
    }


    //emailini deyisdirmek  +++POSTMAN+++
    @PatchMapping("{userId}/email")
    public ResponseEntity<CommonResponse<UserResponse>> updateEmailInUser(@PathVariable Long userId,
                                                                          @RequestBody UserEmailDTO userEmailDTO){
        UserResponse user =  userService.updateEmail(userId, userEmailDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Email ugurla deyisdirildi..", user));
    }

    //Password-nu deyisdirme  +++POSTMAN+++
    @PatchMapping("{userId}/password")
    public ResponseEntity<CommonResponse<UserResponse>> updatePasswordInUser(@PathVariable Long userId,
                                                                             @RequestBody UserPasswordDTO userPasswordDTO){
        UserResponse user =  userService.updatePassword(userId, userPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Sifreniz ugurla deyisdirildi", user));
    }
//
//
//
//
//    //Login
//
//
//    //Logout

////
//    //Ticketler almaq  + ++++Postman++++
    @PatchMapping("/{userId}/tickets/buy")
    public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> buyTickets(@PathVariable Long userId,
                                                      @RequestBody BuyTicketsDTO buyTicketsDTO){
      List<TicketResponseDTO> tickets = ticketService.buyTickets(userId, buyTicketsDTO);
    return ResponseEntity.status(HttpStatus.OK)
            .body(CommonResponse.success("Biletler ugurla alindi..", tickets));
    }

}
