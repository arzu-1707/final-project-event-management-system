package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.ResponceObject;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.service.impl.EventService;
import com.arzuahmed.ticketingsystem.service.impl.PlaceService;
import com.arzuahmed.ticketingsystem.service.impl.UserService;
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

    //butun user-lere baxmaq
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    //id-e gore user axtarisi
    @GetMapping("/user-id/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.findUserById(userId);
    }

    //ada gore user axtarisi
    @GetMapping("/user-name")
    public List<User> getUserByUserName(@RequestParam(name = "name") String userName){
        return userService.findUserByUserName(userName);
    }

    //email-e gore user axtarisi
    @GetMapping("/user-email")
    public User getUserByUserEmail(@RequestBody UserEmailDTO userEmailDTO){
        return userService.findUserByEmail(userEmailDTO);
    }

    //id-e gore user silmek
    @DeleteMapping("/user-id/{userId}")
    public ResponseEntity<ResponceObject> deleteUser(@PathVariable Long userId){
         userService.deleteUser(userId);
         return ResponseEntity.ok(new ResponceObject("User is deleted", userId));
    }

    //Event elave etmek
    @PostMapping("/create/event")
    public ResponseEntity<Event> createEventWithPlaceId(@RequestBody @Valid EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    //Event Silmek
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<ResponceObject> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(new ResponceObject("Delete operations is successfully", eventId));
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
