package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.service.EventService;
import com.arzuahmed.ticketingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/all")
    public List<Event> getAllEvent() {
        return eventService.findAllEvent();
    }

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId){
        return eventService.findEventById(eventId);
    }

    @GetMapping("/eventName")
    public Event getEventByName(@RequestParam String name) {
        return eventService.findEventByName(name);
    }

    @GetMapping("/time")
    public List<Event> findByEventDateBetween(@RequestParam LocalDateTime start,
                                              @RequestParam LocalDateTime end){
        return eventService.findByEventDateBetween(start,end);
    }

    @GetMapping("/ticketAvailable/true")
    public List<Event> findByAvailableTicketsTrue(){
        return eventService.findByAvailableTicketsTrue();
    }

    @PostMapping
    public Event addEvent(@RequestBody EventDTO eventDTO){
        return eventService.addEvent(eventDTO);

    }

    @PutMapping("{eventId}")
    public Event update(@PathVariable Long eventId, @RequestBody EventDTO eventDTO){
        return eventService.updateEvent(eventId, eventDTO);
    }

    @DeleteMapping("{eventId}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEventById(eventId);
        return ResponseEntity.ok("Delete is successfully");
    }
}
