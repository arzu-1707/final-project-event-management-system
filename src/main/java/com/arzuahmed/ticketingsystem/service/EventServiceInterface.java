package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.AddTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventPlaceIdWithTicketsDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
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

    EventPlaceIdWithTicketsDTO createEventWithTickets(EventWithPlaceIdDTO eventDTO);

    void deleteEvent(Long eventId);

    List<Event> findAllEvents();

    List<Event> getEventByName(String eventName);

    EventAndTicketsResponseDTO createEventTicketWithTicketType(EventTicketTicketTypeDTO eventTicketTicketType);

    EventAndTicketsResponseDTO createEventTicketWithTicketType(EventTicketTypeListTicketDTO eventTicketTypeListTicket);

    Event existedEvent(Event event);

    Event existedEventById(Long eventId);

    EventResponseDTO createEvent(@Valid EventDTO eventDTO);

    EventAndPlaceResponseDTO addPlaceInEvent(Long eventId, Long placeId);

    EventAndTicketsResponseDTO addTicketType(Long eventId, List<TicketTypeDTO> ticketTypeDTO);

    EventAndPlaceResponseDTO updatePlace(Long eventId, PlaceDTO placeDTO);

    List<Event> findEventsByPlaceId(Long placeId);

    EventResponseDTO updateEventDate(Long eventId, EventDateDTO eventDateDTO);
}
