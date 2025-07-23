package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.AddTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTypeListTicketDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.TicketAndTicketTypeDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface EventServiceInterface {

    Event findEventById(Long eventId);

    Event findEventByName(String eventName);

    Event createEventWithTickets(EventWithPlaceIdDTO eventDTO);

    void deleteEvent(Long eventId);

    List<Event> findAllEvents();

    List<Event> getEventByName(String eventName);

    Event createEventTicketWithTicketType(EventTicketTicketTypeDTO eventTicketTicketType);

    Event createEventTicketWithTicketType(EventTicketTypeListTicketDTO eventTicketTypeListTicket);

    Event existedEvent(Event event);

    Event existedEventById(Long eventId);

    Event createEvent(@Valid EventDTO eventDTO);

    Event addPlaceInEvent(Long eventId, Long placeId);

    Event addTicketsInEvent(Long eventId, AddTicketsDTO ticketsDTO);

    Event addTicketType(Long eventId, List<TicketTypeDTO> ticketTypeDTO);

    Event updatePlace(Long eventId, PlaceDTO placeDTO);

    List<Event> findEventsByPlaceId(Long placeId);

    Event updateEventDate(Long eventId, EventDateDTO eventDateDTO);
}
