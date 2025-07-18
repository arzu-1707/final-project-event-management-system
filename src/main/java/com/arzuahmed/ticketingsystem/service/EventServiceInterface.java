package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;

import java.util.List;

public interface EventServiceInterface {

    Event findEventById(Long eventId);

    Event findEventByName(String eventName);

    Event createEvent(EventDTO eventDTO);

    void deleteEvent(Long eventId);

    List<Event> findAllEvents();

    List<Event> getEventByName(String eventName);


}
