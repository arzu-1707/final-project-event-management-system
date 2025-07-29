package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.response.CommonResponse;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTypeListTicketDTO;
import com.arzuahmed.ticketingsystem.service.impl.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    //butun user-lere baxmaq     ++++Postmanda+++
    @GetMapping("/all-users")
    public ResponseEntity<CommonResponse<Page<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<UserResponse> user = userService.findAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Emeliyyat Ugurla yerine yetirildi...",user));

    }

    //id-e gore user axtarisi     ++++++Postman+++++
    @GetMapping("/user-id/{userId}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable Long userId){
        UserResponse userResponse =  userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si "+ userId +" olan istifadeci tapildi..",userResponse));
    }

    //ada gore user axtarisi   ++++Postman+++++
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

    //email-e gore user axtarisi    ++++++Postman++++++
    @GetMapping("/user-email")
    public ResponseEntity<CommonResponse<UserResponse>> getUserByUserEmail(@RequestBody UserEmailDTO userEmailDTO){
        UserResponse user = userService.findUserResponseByEmail(userEmailDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(userEmailDTO.getEmail()+ " emaile sahib istifadeci tapildi",
                        user));
    }

    //id-e gore user silmek    +++++Postman+++++
    @DeleteMapping("/user-id/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable Long userId){
         userService.deleteUser(userId);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(CommonResponse.success("Id-si "+userId + " olan istifadeci ugurla silindi..",
                         null));
    }

    //---------------------------------------------------------------------------------------------------------

    //----------------------------------------Event ile bagli--------------------------------------------------

    //Event Yaratmaq   ++++++ Postman+++
    @PostMapping("/create/event")
    public ResponseEntity<CommonResponse<EventResponseDTO>> createEvent(@RequestBody @Valid EventDTO eventDTO){
       EventResponseDTO eventResponseDTO = eventService.createEvent(eventDTO);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(CommonResponse.success("Event ugurla elave edilmisdir...",eventResponseDTO));
    }


    //Event Silmek  ++++++Postman+++
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(CommonResponse.success("Id-si " + eventId
                + " olan Event ugurla silinmisdir...", null));
    }


    //Yaranmis event-a movcud place-i elave etmek  ++++Postman+++
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> addPlaceInEvent(@PathVariable Long eventId,
                                                                                    @RequestBody PlaceIdDTO placeIdDTO){
        EventAndPlaceResponseDTO eventAndPlaceResponseDTO = eventService.addPlaceInEvent(eventId, placeIdDTO.getPlaceId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(eventAndPlaceResponseDTO.getName()
                        + " adli Event-a ID-si " + eventAndPlaceResponseDTO.getPlaceId()
                        + " olan Place ugurla elave edildi..",eventAndPlaceResponseDTO));
    }

    //Event-in Place deyismek (movcud bir place ile)   ++++Postman+++
    @PutMapping("/events/{eventId}/update-place")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> updatePlaceInEvent(@PathVariable Long eventId,
                                                               @RequestBody PlaceDTO placeDTO){
        EventAndPlaceResponseDTO event =eventService.updatePlace(eventId, placeDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(event.getName()
                        + " adli Event-a ID-si " + event.getPlaceId()
                        + " olan Place ile ugurla deyisdirildi...", event));

    }


    //Event tarixini deyisdirmek    +++++Postman+++
    @PatchMapping("/events/{eventId}/update-event-date")
    public ResponseEntity<CommonResponse<EventResponseDTO>> updateEventDate(@PathVariable Long eventId,
                                                            @RequestBody @Valid EventDateDTO eventDateDTO){
        EventResponseDTO event = eventService.updateEventDate(eventId, eventDateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event-in tarixi ugurla deyisdirildi..",event));
    }


    //Event, TicketType (Tekce bir tipe uygun.. mes VIP) ve ticket yaradilmasi   +++Postman+++++
    @PostMapping("/create/event/ticket/ticket-type")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>>  createEventTicketTicketType(@RequestBody
                                                                         EventTicketTicketTypeDTO eventTicketTicketType){
        EventAndTicketsResponseDTO event = eventService.createEventTicketWithTicketType(eventTicketTicketType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Event biletler ile birlikde ugurla yaradildi...",
                        event));
    }

    //Event, TicketType (VIP, Regular ve s ucun) ve ticketler      ++++Postman+++
   @PostMapping("/create/event/tickets/ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> createEventTicketTicketType(@RequestBody EventTicketTypeListTicketDTO eventTicketTypeListTicket){

        EventAndTicketsResponseDTO event = eventService.createEventTicketWithTicketType(eventTicketTypeListTicket);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Event biletler ile birlikde ugurla yaradildi...",event));
    }

    //Movcud Event-a TicketType ve count-a gore ticket elave edilmesi   +++++Postman++
    @PatchMapping("/events/{eventId}/add-tickets-ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> addTicketsAndTicketTypeInEvent(@PathVariable Long eventId,
                                                                           @RequestBody List<TicketTypeDTO> ticketTypeDTO){
        EventAndTicketsResponseDTO event = eventService.addTicketType(eventId, ticketTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Evente biletler elave edildi...",event));
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
