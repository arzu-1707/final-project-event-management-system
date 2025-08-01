package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdAndTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.response.CommonResponse;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndSumPriceResponse;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserInfoResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventWithPlaceIdAndTicketTypeListDTO;
import com.arzuahmed.ticketingsystem.service.impl.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final EventService eventService;
    private final PlaceService placeService;
    private final TicketTypeService ticketTypeService;


    //-------------------------------User ile bagli---------------------------------------------------------

    //butun user-lere baxmaq     ++++Postmanda+++  security
    @GetMapping("/all-users")
    public ResponseEntity<CommonResponse<Page<UserInfoResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<UserInfoResponse> user = userService.findAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Emeliyyat Ugurla yerine yetirildi...",user));

    }

    //id-e gore user axtarisi     ++++++Postman+++++   security
    @GetMapping("/user-id/{userId}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable Long userId){
        UserResponse userResponse =  userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si "+ userId +" olan istifadeci tapildi..",userResponse));
    }

    //ada gore user axtarisi   ++++Postman+++++  security
    @GetMapping("/user-name")
    public ResponseEntity<CommonResponse<Page<UserResponse>>> getUserByUserName(
            @RequestParam(name = "name") String userName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize){
        Pageable pageable =PageRequest.of(pageNumber,pageSize);
        Page<UserResponse> users = userService.findUserByUserName(userName, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla basa catdi...",users));
    }

    //email-e gore user axtarisi    ++++++Postman++++++  security
    @GetMapping("/user-email")
    public ResponseEntity<CommonResponse<UserResponse>> getUserByUserEmail(@RequestBody UserEmailDTO userEmailDTO){
        UserResponse user = userService.findUserResponseByEmail(userEmailDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(userEmailDTO.getEmail()+ " emaile sahib istifadeci tapildi",
                        user));
    }

    //id-e gore user silmek    +++++Postman+++++     security
    @DeleteMapping("/user-id/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si "+userId + " olan istifadeci ugurla silindi..",
                        null));
    }


    //Butun userleri silmek   security
    @DeleteMapping("/all-users")
    public ResponseEntity<CommonResponse<Void>> deleteAllUsers(){
        userService.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Butun userler silindi..", null));
    }



    //---------------------------------------------------------------------------------------------------------

    //----------------------------------------Event ile bagli--------------------------------------------------

    //Event Yaratmaq   ++++++ Postman+++    security
    @PostMapping("/create/event")
    public ResponseEntity<CommonResponse<EventResponseDTO>> createEvent(@RequestBody @Valid EventDTO eventDTO){
       EventResponseDTO eventResponseDTO = eventService.createEvent(eventDTO);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(CommonResponse.success("Event ugurla elave edilmisdir...",eventResponseDTO));
    }

    //Movcud event-a movcud place-i elave etmek  ++++Postman+++   security
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> addPlaceInEvent(@PathVariable Long eventId,
                                                                                    @RequestBody PlaceIdDTO placeIdDTO){
        EventAndPlaceResponseDTO eventAndPlaceResponseDTO = eventService.addPlaceInEvent(eventId, placeIdDTO.getPlaceId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(eventAndPlaceResponseDTO.getName()
                        + " adli Event-a ID-si " + eventAndPlaceResponseDTO.getPlaceId()
                        + " olan Place ugurla elave edildi..",eventAndPlaceResponseDTO));
    }

    //Event-in Place deyismek (movcud bir place ile)   ++++Postman+++   security
    @PutMapping("/events/{eventId}/update-place")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> updatePlaceInEvent(@PathVariable Long eventId,
                                                                                       @RequestBody PlaceDTO placeDTO){
        EventAndPlaceResponseDTO event =eventService.updatePlace(eventId, placeDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(event.getName()
                        + " adli Event-a ID-si " + event.getPlaceId()
                        + " olan Place ile ugurla deyisdirildi...", event));

    }

    //Movcud Event-a TicketType ve count-a gore ticket elave edilmesi   +++++Postman++  security
    @PatchMapping("/events/{eventId}/add-tickets-ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> addTicketsAndTicketTypeInEvent(
            @PathVariable Long eventId,
            @RequestBody List<TicketTypeDTO> ticketTypeDTO){
        EventAndTicketsResponseDTO event = eventService.addTicketType(eventId, ticketTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Evente biletler elave edildi...",event));
    }


    //Yeni Event, TicketType (VIP, Regular ve ya tekce bir tipe uygun) ve ticketler      ++++Postman+++  security
    @PostMapping("/create/event/tickets/ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> createEventTicketTicketType(
            @RequestBody EventWithPlaceIdAndTicketTypeListDTO eventTicketTypeListTicket){

        EventAndTicketsResponseDTO event = eventService.createEventTicketWithTicketType(eventTicketTypeListTicket);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Event biletler ile birlikde ugurla yaradildi...",event));
    }

    //Event-in Available ticket sayina baxmaq ucun  ++Postman++++  security
    @GetMapping("/events/{eventId}/available-ticket/count")
    public ResponseEntity<CommonResponse<Integer>> findCountAvailableTickets(@PathVariable Long eventId){
        Integer count = eventService.findCountAvailableTickers(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Movcud bilet sayi: " + count, count));
    }

    //Event tarixini deyisdirmek    +++++Postman+++  Security
    @PatchMapping("/events/{eventId}/update-event-date")
    public ResponseEntity<CommonResponse<EventResponseDTO>> updateEventDate(@PathVariable Long eventId,
                                                                            @RequestBody @Valid EventDateDTO eventDateDTO){
        EventResponseDTO event = eventService.updateEventDate(eventId, eventDateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event-in tarixi ugurla deyisdirildi..",event));
    }


    //Event-in gelirini hesablamaq       security
    @GetMapping("/events/{eventId}/sum-price")
    public ResponseEntity<CommonResponse<EventAndSumPriceResponse>> calculatePrice(@PathVariable long eventId){
        EventAndSumPriceResponse event = eventService.calculatePrice(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event-e uygun umumi qiymet hesablandi: "+ event.getSumPrice(),
                        event));
    }




    //Event Silmek  ++++++Postman+++  security
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(CommonResponse.success("Id-si " + eventId
                + " olan Event ugurla silinmisdir...", null));
    }

    //Butun eventleri silme   Security
    @DeleteMapping("/all-events")
    public ResponseEntity<CommonResponse> deleteAllEvents(){
        eventService.deleteAllEvents();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Butun Eventler ugurla silindi..."));
    }

//----------------------------------------------------------------------------------------------------------------



//---------------------------------------------Place ile bagli----------------------------------------------------

    //Place Elave etmek   +++++Postman+++++
    @PostMapping("/create/place")
    public ResponseEntity<CommonResponse<PlaceResponse>> createPlace(@RequestBody @Valid PlaceDTO placeDTO ){
        PlaceResponse placeResponse = placeService.createPlace(placeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Place ugurla yaradildi...", placeResponse));
    }

    //place Id-e gore axtaris +++++Postman+++++
    @GetMapping("/places/{placeId}")
    public ResponseEntity<CommonResponse<PlaceResponse>> findPlaceById(@PathVariable Long placeId){
        PlaceResponse place = placeService.findPlaceById(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si " + placeId + " olan place tapildi..", place));
    }


    //place silmek   +++++Postman++++
    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<CommonResponse<Void>> deletePlaceById(@PathVariable Long placeId){
        placeService.deletePlaceById(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si " +placeId +" olan Place silindi...", null));
    }
//------------------------------------------------------------------------------------------------------------


//------------------------------------------- Ticket ile bagli----------------------------------------------




}
