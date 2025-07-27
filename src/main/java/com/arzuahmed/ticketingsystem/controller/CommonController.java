package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.response.CommonResponse;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceWithEventsResponse;
import com.arzuahmed.ticketingsystem.service.impl.CommonService;
import com.arzuahmed.ticketingsystem.service.impl.EventService;
import com.arzuahmed.ticketingsystem.service.impl.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final EventService eventService;
    private final PlaceService placeService;


    //register
    //@PostMapping("/register")
   // public ResponseEntity<User> register(@RequestBody UserDTO userDTO){
     //  return ResponseEntity.ok(commonService.register(userDTO));
    //}

    //login


//----------------------------------------Event ile bagli------------------------------------

    //Eventleri gor   ++++Postman++++++
    @GetMapping("/events")
    public ResponseEntity<CommonResponse<Page<EventResponseDTO>>> findAllEvents(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EventResponseDTO> events = eventService.findAllEvents(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Emeliyyat ugurla yerine yetirildi", events));
    }

    //Eventleri adlarina gore axtar  +++Postman+++++++
    @GetMapping("/events/event-name")
    public ResponseEntity<CommonResponse<Page<EventResponseDTO>>> findEventByName(
            @RequestParam(name = "name") String eventName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<EventResponseDTO> events = eventService.findEventByName(eventName, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(eventName +" adli Event-lar tapildi..", events));
    }
//----------------------------------------------------------------------------------------------------------


//-----------------------------------------Place ile bagli---------------------------------------------------


    //umumi placeleri gore biler  ++++Postman++++
    @GetMapping("/places")
    public ResponseEntity<CommonResponse<Page<PlaceResponse>>> findAllPlaces(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PlaceResponse> places = placeService.findAllPlaces(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla yerine yetirildi..", places));
    }


    //Place adlarina gore axtaris  ++++Postman++++
    @GetMapping("/places/place-name")
    public ResponseEntity<CommonResponse<Page<PlaceResponse>>> findPlacesByName(
            @RequestParam(name = "name") String placeName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
            ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PlaceResponse> places = placeService.findPlaceByName(placeName, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(placeName + " adli place-ler ugurla tapildi..", places));
    }


//------------------------------------------Place ile bagli------------------------------------------------------
    //umumi placelerde kecirilecek tedbirleri gore biler +++Postman++++
    @GetMapping("/places/{placeId}/events")
    public ResponseEntity<CommonResponse<PlaceWithEventsResponse>> findAllEventsByPlaceId(@PathVariable Long placeId){
        PlaceWithEventsResponse place = placeService.findEventsByPlaceId(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla heyata kecirildi...", place));
    }


}
