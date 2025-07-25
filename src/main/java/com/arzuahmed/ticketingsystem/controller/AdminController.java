package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.AddTicketsDTO;
import com.arzuahmed.ticketingsystem.model.response.EventResponseDTO;
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
import com.arzuahmed.ticketingsystem.model.wrapper.TicketAndTicketTypeDTO;
import com.arzuahmed.ticketingsystem.service.impl.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

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




    //---------------------------------Event ile bagli----------------------------------------------

    //Event Yaratmaq   +
    @PostMapping("/create/event")
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDTO eventDTO){
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    //Yaranmis event-i place-e elave etme  +
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<Event> addPlaceInEvent(@PathVariable Long eventId,
                                                 @RequestBody PlaceIdDTO placeIdDTO){
        return ResponseEntity.ok(eventService.addPlaceInEvent(eventId, placeIdDTO.getPlaceId()));
    }

    //Event-in Place deyismek (movcud bir place ile)   +
    @PutMapping("/events/{eventId}/update-place")
    public ResponseEntity<EventResponseDTO> updatePlaceInEvent(@PathVariable Long eventId,
                                                               @RequestBody PlaceDTO placeDTO){
        Event event =eventService.updatePlace(eventId, placeDTO);

        return ResponseEntity.ok(Mapper.eventResponseMapper(event));

    }


    //Event ve ona uygun ticket avtomatik maxtickete gore elave etmek  +
    @PostMapping("/create/event/ticket")
    public ResponseEntity<Event> createEventWithTickets(@RequestBody @Valid EventWithPlaceIdDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEventWithTickets(eventDTO));
    }


    //Movcud Event-e ticket-leri elave etmek +
    @PatchMapping("/events/{eventId}/add-tickets")
    public ResponseEntity<EventResponseDTO> addTicketsInEvent(@PathVariable Long eventId,
                                                   @RequestBody AddTicketsDTO ticketsDTO){
        Event event = eventService.addTicketsInEvent(eventId, ticketsDTO);
        return ResponseEntity.ok(Mapper.eventResponseMapper(event));
    }


    //Event tarixini deyisdirmek    +
    @PatchMapping("/events/{eventId}/update-event-date")
    public ResponseEntity<EventResponseDTO> updateEventDate(@PathVariable Long eventId,
                                                            @RequestBody @Valid EventDateDTO eventDateDTO){
        Event event = eventService.updateEventDate(eventId, eventDateDTO);
        return ResponseEntity.ok(Mapper.eventResponseMapper(event));
    }

    //Movcud Event-e ticketType elave etmek    ??????????????? Mentiqsizdir mence hec bir menasi yoxdur
    //@PutMapping("/eventId/{eventId}/ticket-type")
    //public ResponseEntity<TicketType> addTicketTypeInEvent(@PathVariable Long eventId,
      //                                                    @RequestBody TicketTypeDTO ticketTypeDTO){
       // return ResponseEntity.ok(ticketTypeService.addTicketTypeInEvent(eventId, ticketTypeDTO));
    //}



    //Event, TicketType (Tekce bir tipe uygun.. mes VIP) ve ticket yaradilmasi   +
    @PostMapping("/create/event/ticket/ticket-type")
    public ResponseEntity<EventResponseDTO>  createEventTicketTicketType(@RequestBody
                                                              EventTicketTicketTypeDTO eventTicketTicketType){
        Event event = eventService.createEventTicketWithTicketType(eventTicketTicketType);
        return ResponseEntity.ok(Mapper.eventResponseMapper(event));
    }

    //Event, TicketType (VIP, Regular ve s ucun) ve ticketler      +
   @PostMapping("/create/event/tickets/ticketType")
    public ResponseEntity<EventResponseDTO> createEventTicketTicketType(@RequestBody EventTicketTypeListTicketDTO eventTicketTypeListTicket){

        Event event = eventService.createEventTicketWithTicketType(eventTicketTypeListTicket);
        return ResponseEntity.ok(Mapper.eventResponseDTOMapper(event));
    }

    //Movcud Event-a TicketType ve count-a gore ticket elave edilmesi   +
    @PatchMapping("/events/{eventId}/add-tickets-ticketType")
    public ResponseEntity<EventResponseDTO> addTicketsAndTicketTypeInEvent(@PathVariable Long eventId,
                                                                           @RequestBody List<TicketTypeDTO> ticketTypeDTO){
        Event event = eventService.addTicketType(eventId, ticketTypeDTO);
        return ResponseEntity.ok(Mapper.eventResponseMapper(event));
    }


    //Event Silmek  +
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<ResponseObject> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(new ResponseObject("Ugurla silindi...", eventId));
    }
//-----------------------------------------------------------------------------------------------------------------




    //-----------------------------------------Place ile bagli----------------------------------------------

    //Place Elave etmek   +
    @PostMapping("/create/place")
    public ResponseEntity<Place> createPlace(@RequestBody @Valid PlaceDTO placeDTO){
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
