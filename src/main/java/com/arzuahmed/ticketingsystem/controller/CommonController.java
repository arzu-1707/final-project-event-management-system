package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserLoginRequest;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.response.CommonResponse;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventPlaceNameAndTicketsResponse;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceWithEventsResponse;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketTypeResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketsResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserTokenResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventAndAvailableTicketResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventAndPlaces;
import com.arzuahmed.ticketingsystem.service.impl.CommonService;
import com.arzuahmed.ticketingsystem.service.impl.EventService;
import com.arzuahmed.ticketingsystem.service.impl.PlaceService;
import com.arzuahmed.ticketingsystem.service.impl.TicketService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final EventService eventService;
    private final PlaceService placeService;
    private final CommonService commonService;
    private final TicketService ticketService;


    //register   +++Postman+++  security
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserResponse>> register(@RequestBody UserDTO userDTO){
        UserResponse registeredUser = commonService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(registeredUser.getUserName() + " adli istifadeci ugurla qeydiyyatdan kecdi",
                        registeredUser));
    }

    //login   security
    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        UserTokenResponse login = commonService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(login);
    }



//----------------------------------------Event ile bagli------------------------------------

    //Eventleri gor   ++++Postman++++++       ++++++++++++++SECURITY
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

    //Eventleri adlarina gore axtar  +++Postman+++++++    ++++++SECURITY
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


    //Eventlerin place-ne gore tapmaq  ++Postman++  security
    @GetMapping("/events/{eventName}/places")
    public ResponseEntity<CommonResponse<List<EventAndPlaces>>> findEventWithPlaces(@PathVariable String eventName){
        List<EventAndPlaces> event = eventService.findEventWithPlaces(eventName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(eventName + " adli event-a aid Places ugurla tapildi..", event));
    }

    //tarix araliginda eventler    security
    @GetMapping("/events/")
    public ResponseEntity<CommonResponse<List<EventAndPlaces>>> findEventsBetweenStartDateAndEndDate(
            @RequestParam(required = false) @JsonFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime startDate,
            @RequestParam @JsonFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime endDate
    ) {
        LocalDateTime startDate1 = startDate == null ? LocalDateTime.now() : startDate;

        List<EventAndPlaces> events = eventService.findEventsBetweenStartDateAndEndDate(startDate1, endDate);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla yerine yetirildi..", events));
    }


    //Available biletlere baxmaq  ++Postman++   security
    @GetMapping("/events/{eventId}/available-tickets")
    public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> findAvailableTickets(@PathVariable long eventId){
        List<TicketResponseDTO> tickets = eventService.findAvailableTickets(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Emeliyyat ugurla yerine yetirildi..", tickets));
    }

    //Event-ler ticketler ile birlikde    security
    @GetMapping("/events/tickets")
    public ResponseEntity<CommonResponse<Page<EventPlaceNameAndTicketsResponse>>> getAllEventsAndTickets(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EventPlaceNameAndTicketsResponse> events = eventService.findAllEventsAndTickets(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event ve ona uygun biletler ugurla tapildi..",events));
    }




//----------------------------------------------------------------------------------------------------------


//-----------------------------------------Place ile bagli---------------------------------------------------


    //umumi placeleri gore biler  ++++Postman++++   security
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


    //Place adlarina gore axtaris  ++++Postman++++   security
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


    //PlaceId-e uygun placelerde kecirilecek tedbirleri gore biler +++Postman++++  security
    @GetMapping("/places/{placeId}/events")
    public ResponseEntity<CommonResponse<PlaceWithEventsResponse>> findAllEventsByPlaceId(@PathVariable Long placeId){
        PlaceWithEventsResponse place = placeService.findEventsByPlaceId(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla heyata kecirildi...", place));
    }



    //------------------------------------------Ticket ile bagli------------------------------------------------------

    //Event-a aid biletleri gormek  +++Postman++++  security
    @GetMapping("/events/{eventId}/tickets")
    public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> findAllTickets(
            @PathVariable Long eventId)
    {
        List<TicketResponseDTO> tickets = ticketService.findAllTicketsFromEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla getirildi",
                        tickets));
    }



}
