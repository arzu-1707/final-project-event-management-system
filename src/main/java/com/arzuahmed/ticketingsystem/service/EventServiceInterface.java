package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdAndTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.*;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.*;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface EventServiceInterface {

    Event findEventById(Long eventId);

    void deleteEvent(Long eventId);

    Page<EventResponseDTO> findAllEvents(Pageable pageable);

    Page<EventResponseDTO> findEventByName(String eventName, Pageable pageable);

    EventAndTicketsResponseDTO createEventTicketWithTicketType(EventWithPlaceIdAndTicketTypeDTO eventTicketTicketType);

    EventAndTicketsResponseDTO createEventTicketWithTicketType(EventWithPlaceIdAndTicketTypeListDTO eventTicketTypeListTicket);

    Event existedEvent(Event event);

    Event existedEventById(Long eventId);

    EventResponseDTO createEvent(@Valid EventDTO eventDTO);

    EventAndPlaceResponseDTO addPlaceInEvent(Long eventId, Long placeId);

    EventAndTicketsResponseDTO addTicketType(Long eventId, List<TicketTypeDTO> ticketTypeDTO);

    EventAndPlaceResponseDTO updatePlace(Long eventId, PlaceDTO placeDTO);

    List<Event> findEventsByPlaceId(Long placeId);

    EventResponseDTO updateEventDate(Long eventId, EventDateDTO eventDateDTO);

    List<Event> findEventByName(String name);


    Integer findCountAvailableTickers(Long eventId);

    List<TicketResponseDTO> findAvailableTickets(long eventId);

    List<EventAndPlaces> findEventWithPlaces(String eventName);


    void deleteAllEvents();

    Page<EventAndPlaces> findEventsBetweenStartDateAndEndDate(LocalDateTime startDate1, LocalDateTime endDate, Pageable pageable);

    EventAndSumPriceResponse calculatePrice(long eventId);

    Page<EventPlaceNameAndTicketsResponse> findAllEventsAndTickets(Pageable pageable);
}
