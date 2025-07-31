package com.arzuahmed.ticketingsystem.controller;


import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserPasswordDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
   @GetMapping("/tickets")
  public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> getAllTickets() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userEmail = authentication.getName();


       User user = userService.findByEmail(userEmail).orElseThrow(() ->
               new UserNotFound(ErrorCode.USER_NOT_FOUND));


       List<TicketResponseDTO> tickets = ticketService.findAllTickets(user.getId());
       return ResponseEntity.status(HttpStatus.OK)
               .body(CommonResponse.success("Ugurla yerine yetirildi...",tickets ));
    }


    //emailini deyisdirmek  +++POSTMAN+++   security
    @PatchMapping("/email")
    public ResponseEntity<CommonResponse<UserResponse>> updateEmailInUser(@RequestBody UserEmailDTO userEmailDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));
        UserResponse userResponse =  userService.updateEmail(user.getId(), userEmailDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Email ugurla deyisdirildi..", userResponse));
    }

    //Password-nu deyisdirme  +++POSTMAN+++   security
    @PatchMapping("/password")
    public ResponseEntity<CommonResponse<UserResponse>> updatePasswordInUser(
            @RequestBody UserPasswordDTO userPasswordDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));
        UserResponse userResponse =  userService.updatePassword(user.getId(), userPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Sifreniz ugurla deyisdirildi", userResponse));
    }


//    //Ticketler almaq  + ++++Postman++++
    @PatchMapping("/tickets/buy")
    public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> buyTickets(
                                                      @RequestBody BuyTicketsDTO buyTicketsDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));

        List<TicketResponseDTO> tickets = ticketService.buyTickets(user.getId(), buyTicketsDTO);
    return ResponseEntity.status(HttpStatus.OK)
            .body(CommonResponse.success("Biletler ugurla alindi..", tickets));
    }

}
