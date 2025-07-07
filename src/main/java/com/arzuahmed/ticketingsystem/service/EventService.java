package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFound;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.repository.EventRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepositoryInterface eventRepository;

    public Event addEvent(EventDTO eventDTO) {
       Event event = Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .eventDate(eventDTO.getEventDate())
                .availableTickets(eventDTO.getAvailableTickets())
                .location(eventDTO.getLocation())
                .maxTickets(eventDTO.getMaxTickets())
                .build();
       return eventRepository.save(event);
    }

    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    public Event findEventById(Long eventId) {
        Event eventById = eventRepository.findEventById(eventId);
        if (eventById == null) {
            throw new EventNotFound("Event is not found");
        }
        return eventById;
    }

    public Event findEventByName(String name) {
        Event eventByName = eventRepository.findEventByName(name);
        if (eventByName == null){
            throw new EventNotFound("Event is not found");
        }
        return eventByName;
    }

    public List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByEventDateBetween(start, end);
    }

    public List<Event> findByAvailableTicketsTrue() {
        return eventRepository.findByAvailableTicketsTrue();
    }

    public void deleteEventById(Long eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (event==null) {
            throw new EventNotFound("Event not found");
        }
        eventRepository.delete(event);
    }

    public Event updateEvent(Long eventId, EventDTO eventDTO) {
        Event eventById = findEventById(eventId);
        eventById.setName(eventDTO.getName());
        eventById.setLocation(eventDTO.getLocation());
        eventById.setDescription(eventDTO.getDescription());
        eventById.setMaxTickets(eventDTO.getMaxTickets());
        eventById.setAvailableTickets(eventDTO.getAvailableTickets());
        eventById.setEventDate(eventDTO.getEventDate());
        return eventRepository.save(eventById);
    }
}
