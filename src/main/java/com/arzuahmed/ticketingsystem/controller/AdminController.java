package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.AddTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.response.CommonResponse;
import com.arzuahmed.ticketingsystem.model.response.ResponseObject;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventPlaceIdWithTicketsDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTypeListTicketDTO;
import com.arzuahmed.ticketingsystem.service.impl.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    //butun user-lere baxmaq     +
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    //id-e gore user axtarisi     +
    @GetMapping("/user-id/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.findUserById(userId);
    }

    //ada gore user axtarisi   +
    @GetMapping("/user-name")
    public List<User> getUserByUserName(@RequestParam(name = "name") String userName){
        return userService.findUserByUserName(userName);
    }

    //email-e gore user axtarisi    +
    @GetMapping("/user-email")
    public User getUserByUserEmail(@RequestBody UserEmailDTO userEmailDTO){
        return userService.findUserByEmail(userEmailDTO);
    }

    //id-e gore user silmek    +
    @DeleteMapping("/user-id/{userId}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long userId){
         userService.deleteUser(userId);
         return ResponseEntity.ok(new ResponseObject("Istifadeci silindi..", userId));
    }

    //---------------------------------------------------------------------------------------------------------

    //----------------------------------------Event ile bagli--------------------------------------------------

    //Event Yaratmaq   ++++++ Postman+++ isleyir
    @PostMapping("/create/event")
    public ResponseEntity<CommonResponse<EventResponseDTO>> createEvent(@RequestBody @Valid EventDTO eventDTO){
       EventResponseDTO eventResponseDTO = eventService.createEvent(eventDTO);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(CommonResponse.success("Event ugurla elave edilmisdir...",eventResponseDTO));
    }


    //Event Silmek  ++++++Postman+++ isledi
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(CommonResponse.success("Id-si " + eventId
                + " olan Event ugurla silinmisdir...", null));
    }


    //Yaranmis event-a movcud place-i elave etmek  ++++Postman+++ isledi
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> addPlaceInEvent(@PathVariable Long eventId,
                                                                                    @RequestBody PlaceIdDTO placeIdDTO){
        EventAndPlaceResponseDTO eventAndPlaceResponseDTO = eventService.addPlaceInEvent(eventId, placeIdDTO.getPlaceId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(eventAndPlaceResponseDTO.getName()
                        + " adli Event-a ID-si " + eventAndPlaceResponseDTO.getPlaceId()
                        + " olan Place ugurla elave edildi..",eventAndPlaceResponseDTO));
    }




    //Event-in Place deyismek (movcud bir place ile)   ++++Postman ++ isledi
    @PutMapping("/events/{eventId}/update-place")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> updatePlaceInEvent(@PathVariable Long eventId,
                                                               @RequestBody PlaceDTO placeDTO){
        EventAndPlaceResponseDTO event =eventService.updatePlace(eventId, placeDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(event.getName()
                        + " adli Event-a ID-si " + event.getPlaceId()
                        + " olan Place ile ugurla deyisdirildi...", event));

    }





    //Event tarixini deyisdirmek    +
    @PatchMapping("/events/{eventId}/update-event-date")
    public ResponseEntity<CommonResponse<EventResponseDTO>> updateEventDate(@PathVariable Long eventId,
                                                            @RequestBody @Valid EventDateDTO eventDateDTO){
        EventResponseDTO event = eventService.updateEventDate(eventId, eventDateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event-in tarixi ugurla deyisdirildi..",event));
    }


    //Event, TicketType (Tekce bir tipe uygun.. mes VIP) ve ticket yaradilmasi   +++Postman+++++ isledi
    @PostMapping("/create/event/ticket/ticket-type")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>>  createEventTicketTicketType(@RequestBody
                                                                         EventTicketTicketTypeDTO eventTicketTicketType){
        EventAndTicketsResponseDTO event = eventService.createEventTicketWithTicketType(eventTicketTicketType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Event biletler ile birlikde ugurla yaradildi...",
                        event));

    }

    //Event, TicketType (VIP, Regular ve s ucun) ve ticketler      ++++Postman+++ isledi
   @PostMapping("/create/event/tickets/ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> createEventTicketTicketType(@RequestBody EventTicketTypeListTicketDTO eventTicketTypeListTicket){

        EventAndTicketsResponseDTO event = eventService.createEventTicketWithTicketType(eventTicketTypeListTicket);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Event biletler ile birlikde ugurla yaradildi...",event));
    }

    //Movcud Event-a TicketType ve count-a gore ticket elave edilmesi   +++++Postman++ isledi
    @PatchMapping("/events/{eventId}/add-tickets-ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> addTicketsAndTicketTypeInEvent(@PathVariable Long eventId,
                                                                           @RequestBody List<TicketTypeDTO> ticketTypeDTO){
        EventAndTicketsResponseDTO event = eventService.addTicketType(eventId, ticketTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Evente biletler elave edildi...",event));
    }

//----------------------------------------------------------------------------------------------------------------



//---------------------------------------------Place ile bagli----------------------------------------------------

    //Place Elave etmek   +
    @PostMapping("/create/place")
    public ResponseEntity<Place> createPlace(@RequestBody @Valid PlaceDTO placeDTO ){
        return ResponseEntity.ok(placeService.createPlace(placeDTO));
    }

    //place Id-e gore axtaris +
    @GetMapping("/places/{placeId}")
    public Place findPlaceById(@PathVariable Long placeId){
        return placeService.findPlaceById(placeId);
    }


    //place silmek   +
    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<ResponseObject> deletePlaceById(@PathVariable Long placeId){
        placeService.deletePlaceById(placeId);
        return ResponseEntity.ok(new ResponseObject("Place ugurla silindi..", placeId) );
    }
//------------------------------------------------------------------------------------------------------------


//------------------------------------------- TicketType ile bagli----------------------------------------------

    //TicketType elave etmek
//    @PostMapping("/add-ticket-types")
//    public ResponseEntity<TicketType> addTicketType(@RequestBody TicketTypeDTO ticketTypeDTO){
//        return ResponseEntity.ok(placeService.addTicketType(ticketTypeDTO));
//    }


}
