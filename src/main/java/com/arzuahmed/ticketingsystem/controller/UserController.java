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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Operations" , description = "Bu metodlardan hem admin, hem de login olmus user-ler istifade ede biler")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;


    //User-in ticketlerini axtaris +++POSTMAN++security
    @Operation(summary = "User-in ticketlerin axtarisi",
    description = "User-in aldigi biletlere baxmaq ucun istifade olunur",
    tags = {"User Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
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


    @Operation(summary = "Oz profiline baxis", description = "Oz melumatlarini gormek ucun istifade olunur",
    tags = {"User Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/my-profile/about-me")
    public ResponseEntity<CommonResponse<UserResponse>> getMyProfile(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));
        UserResponse me = userService.findUserById(user.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Butun melumatlarin ugurla getirildi..", me));
    }




    //emailini deyisdirmek  +++POSTMAN+++   security
    @Operation(summary = "User-in Email deyisdirilmesi",
            description = "User-in ozunun email-nin deyisdirilmesi ucun istifade olunur",
            tags = {"User Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
    @Operation(summary = "User-in password-nun deyisdirilmesi",
            description = "User-in password-nun deyisdirilmesi ucun istifade olunur",
            tags = {"User Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
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

  //hesabini deaktiv etmek              security
  @Operation(summary = "User-in hesabinin deaktiv olunmasi",
          description = "User oz hesabini dondurulmasi ucun istifade olunur",
          tags = {"User Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/my-account/deactivate")
    public ResponseEntity<CommonResponse<Void>> deactivateMyAccount(){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));

        userService.deleteUser(user.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Hesabiniz deaktiv edildi..", null));
    }

//    //Ticketler almaq  + ++++Postman++++   security
@Operation(summary = "Ticket alinmasi",
        description = "User-in bilet ve ya biletler almaq ucun istifade olunur",
        tags = {"User Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
@PatchMapping("/tickets/buy")
    public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> buyTickets(
                                                      @RequestBody BuyTicketsDTO buyTicketsDTO
                                                      ){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    String email = auth.getName();
    User user = userService.findByEmail(email).orElseThrow(() -> new UserNotFound(ErrorCode.USER_NOT_FOUND));

    List<TicketResponseDTO> ticketResponseDTOS = ticketService.buyTickets(user.getId(), buyTicketsDTO);
    return ResponseEntity.status(HttpStatus.OK)
            .body(CommonResponse.success("Biletler ugurla alindi..", ticketResponseDTOS));
    }

}
