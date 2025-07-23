package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.service.impl.CommonService;
import com.arzuahmed.ticketingsystem.service.impl.EventService;
import com.arzuahmed.ticketingsystem.service.impl.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;
    private final EventService eventService;
    private final PlaceService placeService;


    //register
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO){
       return ResponseEntity.ok(commonService.register(userDTO));
    }

    //login



    //Eventleri gor
    @GetMapping("/events")
    public List<Event> findAllEvents(){
        return eventService.findAllEvents();
    }

    //Eventleri adlarina gore axtar
    @GetMapping("/events/event-name")
    public List<Event> findEventByName(@RequestParam(name = "name") String eventName){
        return eventService.getEventByName(eventName);
    }

    //Place adlarina gore axtaris
    @GetMapping("/places/place-name")
    public List<Place> findPlacesByName(@RequestParam(name = "name") String placeName){
        return placeService.findPlaceByName(placeName);
    }

    //umumi placeleri gore biler
    @GetMapping("/places")
    public List<Place> findAllPlaces(){
        return placeService.findAllPlaces();
    }

    //umumi placelerde kecirilecek tedbirleri gore biler
    @GetMapping("/places/{placeId}/events")
    public List<Event> findAllEventsByPlaceId(@PathVariable Long placeId){
        return eventService.findEventsByPlaceId(placeId);
    }


}
