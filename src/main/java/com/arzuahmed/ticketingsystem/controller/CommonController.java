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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Common Operations", description = "Bu metodlari istifade etmek ucun register ve ya login olmaq ehtiyyac yoxdur")
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final EventService eventService;
    private final PlaceService placeService;
    private final CommonService commonService;
    private final TicketService ticketService;


    @Operation(summary = "Register",
            description = "Butun istifadecilerin register olunmasi (USER ROLE)",
    tags = {"Common Operations"})
    //register   +++Postman+++  security
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserResponse>> register(@RequestBody UserDTO userDTO){
        UserResponse registeredUser = commonService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(registeredUser.getUserName() + " adli istifadeci ugurla qeydiyyatdan kecdi",
                        registeredUser));
    }

    //login   security
    @Operation(summary = "Login", description = "Login olunmasi (Hem ADMIN, hem de USER)",
            tags = {"Common Operations"})
    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        UserTokenResponse login = commonService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(login);
    }



//----------------------------------------Event ile bagli------------------------------------

    //Eventleri gor   ++++Postman++++++       ++++++++++++++SECURITY
    @Operation(summary = "Umumi Eventler", description = "Butun Eventlerin getirilmesi",
            tags = {"Common Operations"})
    @GetMapping("/events")
    public ResponseEntity<CommonResponse<Page<EventResponseDTO>>> findAllEvents(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam boolean asc
    ){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<EventResponseDTO> events = eventService.findAllEvents(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Emeliyyat ugurla yerine yetirildi", events));
    }

    //Eventleri adlarina gore axtar  +++Postman+++++++    ++++++SECURITY
    @Operation(summary = "Ada gore axtaris", description = "Event-in adina gore axtarisi",
            tags = {"Common Operations"})
    @GetMapping("/events/event-name")
    public ResponseEntity<CommonResponse<Page<EventResponseDTO>>> findEventByName(
            @RequestParam(name = "name") String eventName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam boolean asc){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<EventResponseDTO> events = eventService.findEventByName(eventName, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(eventName +" adli Event-lar tapildi..", events));
    }



    @Operation(summary = "Event-in kecireleceyi place-lerin axtarisi",
            description = "Event name-e gore Place-lerin axtarisi",
            tags = {"Common Operations"})
    @GetMapping("/events/{eventName}/places")
    public ResponseEntity<CommonResponse<List<EventAndPlaces>>> findEventWithPlaces(@PathVariable String eventName){
        List<EventAndPlaces> event = eventService.findEventWithPlaces(eventName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(eventName + " adli event-a aid Places ugurla tapildi..", event));
    }

    //tarix araliginda eventler    security
    @Operation(summary = "Tarixe gore axtaris", description = "Mueyyen tarix araliginda axtaris",
            tags = {"Common Operations"})
    @GetMapping("/events/between-date")
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
    @Operation(summary = "Satilmamis biletlerin axtarisi",
            description = "EventId-e uygun satilmamis biletlerin axtarisi",
            tags = {"Common Operations"})
    @GetMapping("/events/{eventId}/available-tickets")
    public ResponseEntity<CommonResponse<List<TicketResponseDTO>>> findAvailableTickets(@PathVariable long eventId){
        List<TicketResponseDTO> tickets = eventService.findAvailableTickets(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Emeliyyat ugurla yerine yetirildi..", tickets));
    }

    //Event-ler ticketler ile birlikde    security
    @Operation(summary = "Butun Event-lerin ve ona uygun ticketlerin axtarisi",
            description = "Butun Eventlerin ve onlarin Ticketlerin getirilmesi",
            tags = {"Common Operations"})
    @GetMapping("/events/tickets")
    public ResponseEntity<CommonResponse<Page<EventPlaceNameAndTicketsResponse>>> getAllEventsAndTickets(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam boolean asc
    ){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<EventPlaceNameAndTicketsResponse> events = eventService.findAllEventsAndTickets(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event ve ona uygun biletler ugurla tapildi..",events));
    }




//----------------------------------------------------------------------------------------------------------


//-----------------------------------------Place ile bagli---------------------------------------------------


    //umumi placeleri gore biler  ++++Postman++++   security
    @Operation(summary = "Umumi Place-ler",
            description = "Umumi Place-ler ve onlar haqqinda informasiya",
            tags = {"Common Operations"})
    @GetMapping("/places")
    public ResponseEntity<CommonResponse<Page<PlaceResponse>>> findAllPlaces(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam boolean asc
    ){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<PlaceResponse> places = placeService.findAllPlaces(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla yerine yetirildi..", places));
    }


    //Place adlarina gore axtaris  ++++Postman++++   security
    @Operation(summary = "PlaceName-e gore axtaris",
            description = "Place-nin adina uygun Place-lerin informasiyasi",
            tags = {"Common Operations"})
    @GetMapping("/places/place-name")
    public ResponseEntity<CommonResponse<Page<PlaceResponse>>> findPlacesByName(
            @RequestParam(name = "name") String placeName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String sortBy,
            @RequestParam boolean asc
            ){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<PlaceResponse> places = placeService.findPlaceByName(placeName, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(placeName + " adli place-ler ugurla tapildi..", places));
    }


    //PlaceId-e uygun placelerde kecirilecek tedbirleri gore biler +++Postman++++  security
    @Operation(summary = "PlaceId-ne gore Eventler",
            description = "PalecId-e gore Place-in kecireceyi eventlerin siyahisi",
            tags = {"Common Operations"})
    @GetMapping("/places/{placeId}/events")
    public ResponseEntity<CommonResponse<PlaceWithEventsResponse>> findAllEventsByPlaceId(@PathVariable Long placeId){
        PlaceWithEventsResponse place = placeService.findEventsByPlaceId(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla heyata kecirildi...", place));
    }

    @Operation(summary = "Place Name-e gore eventler", description = "Place Name-e gore Eventleri axtaris edir",
    tags = {"Common Operations"})
    @GetMapping("/place/{placeName}/events")
    public ResponseEntity<CommonResponse<PlaceWithEventsResponse>> findAllEventByPlaceName(
            @PathVariable String placeName
    ){
        PlaceWithEventsResponse place = placeService.findEventsByPlaceName(placeName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla heyata kecirildi..", place));
    }



    //------------------------------------------Ticket ile bagli------------------------------------------------------

    //Event-a aid biletleri gormek  +++Postman++++  security
    @Operation(summary = "Event-e aid biletler",
            description = "EventId-e gore umumi ticketler",
            tags = {"Common Operations"})
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
