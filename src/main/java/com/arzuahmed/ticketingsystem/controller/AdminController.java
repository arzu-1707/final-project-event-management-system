package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.response.ResponseObject;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTypeListTicketDTO;
import com.arzuahmed.ticketingsystem.service.impl.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


                     // User ile bagli
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




                            //Event ile bagli

    //Event Yaratmaq   +
    @PostMapping("/create/event")
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDTO eventDTO){
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    //Yaranmis event-i place-e elave etmek
    //@PatchMapping("/events/{eventId}/")



    //Event ve ona uygun ticket avtomatik maxtickete gore elave etmek  +
    @PostMapping("/create/event/ticket")
    public ResponseEntity<Event> createEventWithTickets(@RequestBody @Valid EventWithPlaceIdDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEventWithTickets(eventDTO));
    }


    //Movcud Event-e ticketType elave etmek
    @PutMapping("/eventId/{eventId}/ticket-type")
    public ResponseEntity<TicketType> addTicketTypeInEvent(@PathVariable Long eventId,
                                                           @RequestBody TicketTypeDTO ticketTypeDTO){
        return ResponseEntity.ok(ticketTypeService.addTicketTypeInEvent(eventId, ticketTypeDTO));

    }

    //Event, TicketType (Tekce bir tipe uygun.. mes VIP) ve ticket yaradilmasi
    @PostMapping("/create/event/ticket/ticket-type")
    public ResponseEntity<Event>  createEventTicketTicketType(@RequestBody
                                                              EventTicketTicketTypeDTO eventTicketTicketType){
        return ResponseEntity.ok(eventService.createEventTicketWithTicketType(eventTicketTicketType));
    }

    //Event, TicketType (VIP, Regular ve s ucun) ve ticketler
    @PostMapping("/create/event/tickets/ticketType")
    public ResponseEntity<Event> createEventTicketTicketType(@RequestBody EventTicketTypeListTicketDTO eventTicketTypeListTicket){
        return ResponseEntity.ok(eventService.createEventTicketWithTicketType(eventTicketTypeListTicket));
    }


    //Event Silmek
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<ResponseObject> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(new ResponseObject("Ugurla silindi...", eventId));
    }


    //Place Elave etmek
    @PostMapping("/create/place")
    public ResponseEntity<Place> createPlace(@RequestBody @Valid PlaceDTO placeDTO){
        return ResponseEntity.ok(placeService.createPlace(placeDTO));
    }

    //place Id-e gore axtaris
    @GetMapping("/places/{placeId}")
    public Place findPlaceById(@PathVariable Long placeId){
        return placeService.findPlaceById(placeId);
    }

}
