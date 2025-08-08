package com.arzuahmed.ticketingsystem.service.impl;


import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventExistsException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceNotFoundException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketNotFoundException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketTypeNotFound;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketTypesNotFoundException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdAndTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.*;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventAndPlaces;
import com.arzuahmed.ticketingsystem.model.wrapper.EventWithPlaceIdAndTicketTypeListDTO;
import com.arzuahmed.ticketingsystem.repository.EventRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.PlaceRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.TicketRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.TicketTypeRepository;
import com.arzuahmed.ticketingsystem.service.EventServiceInterface;
import com.arzuahmed.ticketingsystem.validate.Validate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements EventServiceInterface {

    private final EventRepositoryInterface eventRepository;
    private final PlaceService placeService;

    @Lazy
    @Autowired    //circular olurdu ona gore lazy+autowired istifade etdim
    private TicketService ticketService;
    private final TicketTypeRepository ticketTypeRepository;
    @Autowired
    private TicketTypeService ticketTypeService;
    @Autowired
    private PlaceRepositoryInterface placeRepositoryInterface;
    @Autowired
    private TicketRepositoryInterface ticketRepositoryInterface;

    @Override
    public Event findEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()->
                new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEvent(Long eventId) {
        Event event = findEventById(eventId);
        Place place = event.getPlace();
        if (place != null){
        place.removeEvent(event);}
        eventRepository.delete(existedEvent(event));
        log.info("Event is deleted. EventId: {} and EventName: {} ", event.getId(), event.getName());
    }

    @Override
    public Page<EventResponseDTO> findAllEvents(Pageable pageable) {
        Page<Event> allEvents = eventRepository.findAll(pageable);
        if (allEvents.isEmpty()){
            throw new EventsNotFoundException(ErrorCode.EVENTS_NOT_FOUND);
        }
        return Mapper.eventResponsePageMapper(allEvents);
    }

    @Override
    public Page<EventResponseDTO> findEventByName(String eventName, Pageable pageable) {
        Page<Event> events = eventRepository.findAllByNameEqualsIgnoreCase(eventName, pageable);
        if(events.isEmpty()){
            throw new EventsNotFoundException(ErrorCode.EVENTS_NOT_FOUND);
        }
        return Mapper.eventResponsePageMapper(events);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EventAndTicketsResponseDTO createEventTicketWithTicketType(EventWithPlaceIdAndTicketTypeDTO fullEvent) {

        Place place = placeService.findById(fullEvent.getPlaceId());
        Event event = Mapper.eventMapperFromEventWithPlaceIdAndTicketTypeDTO(fullEvent);
        event.setPlace(place);
        place.addEvent(event);

        Validate.validateMaxTicketAndSeatCapacity(fullEvent.getMaxTickets(), place.getSeatCapacity());
        Validate.validateTicketCountAndMaxTicket(fullEvent.getTicketTypeDTO().getTicketCount(),
                fullEvent.getMaxTickets());

        Event savedEvent = eventRepository.save(event);

        TicketType ticketType = Mapper.ticketTypeMapperFromDTO(fullEvent.getTicketTypeDTO());
        ticketType.setEvent(savedEvent);
        TicketType savedTicketType = ticketTypeRepository.save(ticketType);


        List<Ticket> tickets = new ArrayList<>();

        int ticketCounter = 1;
        for (int i = 1; i <= fullEvent.getTicketTypeDTO().getTicketCount(); i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketNo(ticketCounter++);
            ticket.setEvent(savedEvent);
            ticket.setStatus(STATUS.AVAILABLE);
            ticket.setTicketType(savedTicketType);
            tickets.add(ticket);
        }

         ticketService.saveAll(tickets);
         savedEvent.setTickets(tickets);

        return Mapper.eventAndTicketsResponseDTOMapperFromEvent(savedEvent);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EventAndTicketsResponseDTO createEventTicketWithTicketType(
            EventWithPlaceIdAndTicketTypeListDTO fullEvent) {
        Place place
                = placeService.findById(fullEvent.getPlaceId());

        Event event = Mapper.eventMapperFromEventWithPlaceIdAndTicketTypeListDTO(fullEvent);

        event.setPlace(place);
        place.addEvent(event);

        Validate.validateMaxTicketAndSeatCapacity(fullEvent.getMaxTickets(), event.getPlace().getSeatCapacity());
        Validate.validateTicketCountsFromTypeDTOAndMaxTicket(fullEvent.getTicketTypeDTO(), fullEvent.getMaxTickets());

        Event savedEvent = eventRepository.save(event);


        List<TicketType> ticketTypeList = Mapper.ticketTypeListMapperFromTicketTypeDTO(fullEvent.getTicketTypeDTO());
        for (TicketType ticketType: ticketTypeList){
            ticketType.setEvent(savedEvent);
            ticketTypeRepository.save(ticketType);
        }


        List<Ticket> tickets = new ArrayList<>();
        int ticketCounter = 1;
        for (int i=0; i<fullEvent.getTicketTypeDTO().size(); i++){

            TicketTypeDTO ticketTypeDTO = fullEvent.getTicketTypeDTO().get(i);
            TicketType ticketType = ticketTypeList.get(i);

            for (int j = 1; j <= ticketTypeDTO.getTicketCount(); j++) {
                Ticket ticket = new Ticket();
                ticket.setTicketNo(ticketCounter++);
                ticket.setEvent(savedEvent);
                ticket.setTicketType(ticketType);
                ticket.setStatus(STATUS.AVAILABLE);
                tickets.add(ticket);
            }
        }
        ticketService.saveAll(tickets);
        savedEvent.setTickets(tickets);

       return Mapper.eventAndTicketsResponseDTOMapperFromEvent(savedEvent);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Event existedEvent(Event event){
        if (event == null) {
            throw new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND);
        }
        return event;
    }

    @Override
    public Event existedEventById(Long eventId){
        Event event = eventRepository.findEventById(eventId).orElseThrow(()-> new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND));
        if (event == null) {
            throw new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND);
        }
        return event;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponseDTO createEvent(EventDTO eventDTO) {
       if (eventRepository.existsEventsByEventDate(eventDTO.getEventDate())){
           throw new EventExistsException(ErrorCode.EVENT_ALREADY_EXITS);
        }
        Event event = Mapper.eventMapper(eventDTO);
        Event savedEvent = eventRepository.save(event);
       return Mapper.eventResponseMapper(savedEvent);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EventAndPlaceResponseDTO addPlaceInEvent(Long eventId, Long placeId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND));
        Place place = placeService.findById(placeId);

        if (place==null){
            throw new PlaceNotFoundException(ErrorCode.PLACE_NOT_FOUND);
        }

        Validate.validateMaxTicketAndSeatCapacity(event.getMaxTickets(), place.getSeatCapacity());

        event.setPlace(place);
        place.addEvent(event);
        Event savedEvent = eventRepository.save(event);
        return Mapper.eventAndPlaceResponseDTO(savedEvent);
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EventAndTicketsResponseDTO addTicketType(Long eventId, List<TicketTypeDTO> ticketTypeDTO) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND));
        List<Ticket> tickets = new ArrayList<>();

        Validate.validateTicketCountsFromTypeDTOAndMaxTicket(ticketTypeDTO, event.getMaxTickets());

        int counter = 1;
       for(TicketTypeDTO ticketType : ticketTypeDTO){
           TicketType ticketType1 = ticketTypeRepository.save(Mapper.ticketTypeMapperFromDTO(ticketType));
           for (int i = 1; i <= ticketType.getTicketCount(); i++) {
               Ticket ticket = new Ticket();
               ticket.setTicketType(ticketType1);
               ticket.setEvent(event);
               ticket.setStatus(STATUS.AVAILABLE);
               ticket.setTicketNo(counter++);
               tickets.add(ticket);
               event.addTicket(ticket);
           }
       }

        Event savedEvent = eventRepository.save(event);

       return Mapper.eventAndTicketsResponseDTOMapperFromEvent(savedEvent);

    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EventAndPlaceResponseDTO updatePlace(Long eventId, PlaceDTO placeDTO) {
        Event event = eventRepository.findById(eventId).orElseThrow(()->
                new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND));
      Place place = placeService.findPlaceByNameAndLocation(placeDTO.getPlaceName(), placeDTO.getLocation());
      if (place == null){
          placeService.createPlace(placeDTO);
          place = placeService.findPlaceByNameAndLocation(placeDTO.getPlaceName(), placeDTO.getLocation());
      }

       Validate.validateMaxTicketAndSeatCapacity(event.getMaxTickets(), place.getSeatCapacity());

        event.setPlace(place);
        place.addEvent(event);
        Event savedEvent = eventRepository.save(event);
        return Mapper.eventAndPlaceResponseDTO(savedEvent);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Event> findEventsByPlaceId(Long placeId){
        Place place = placeService.findById(placeId);
        List<Event> events = place.getEvents();
        if (events.isEmpty()){
            throw new EventsNotFoundException(ErrorCode.EVENTS_NOT_FOUND);
        }
        return events;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponseDTO updateEventDate(Long eventId, EventDateDTO eventDateDTO) {
        Event event = findEventById(eventId);
        event.setEventDate(eventDateDTO.getEventDate());
       return Mapper.eventResponseMapper(event);
    }

    @Override
    public List<Event> findEventByName(String name) {
      List<Event> events =  eventRepository.findEventByNameIsLikeIgnoreCase(name);
      if (events.isEmpty()){
          throw new EventsNotFoundException(ErrorCode.EVENTS_NOT_FOUND);
      }
      return events;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Integer findCountAvailableTickers(Long eventId) {
        Event event = findEventById(eventId);
        return event.getAvailableTickets();
    }

    @Override
    public List<TicketResponseDTO> findAvailableTickets(long eventId) {
        Event event = findEventById(eventId);
        List<Ticket> tickets = event.getTickets()
                .stream()
                .filter(ticket -> ticket.getStatus().equals(STATUS.AVAILABLE))
                .toList();

      return Mapper.ticketResponseDTOMapper(tickets);
    }

    @Override
    public List<EventAndPlaces> findEventWithPlaces(String eventName) {

        List<Event> events = eventRepository.findEventsByNameIgnoreCase(eventName);

       return events.stream()
                        .map(event -> Mapper.eventAndPlacesMapper(event))
                                .toList();
    }



    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    @Override
    public Page<EventAndPlaces> findEventsBetweenStartDateAndEndDate(LocalDateTime startDate1, LocalDateTime endDate, Pageable pageable) {
        Page<Event> events = eventRepository.findEventsByEventDateBetween(startDate1, endDate, pageable);
       return events
                .map(event -> Mapper.eventAndPlacesMapper(event));
    }

    @Override
    public EventAndSumPriceResponse calculatePrice(long eventId) {
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND));

        Place place = placeService.findById(event.getPlace().getId());

        List<Ticket> tickets = ticketRepositoryInterface.findByEvent_IdAndStatus(eventId, STATUS.SOLD);

        if (tickets.isEmpty()){
            throw new TicketNotFoundException(ErrorCode.TICKET_NOT_FOUND);
        }

       Double sumPrice = tickets.stream().map(ticket -> ticket.getTicketType().getPrice())
                .reduce(Double::sum).orElseThrow();

        EventAndSumPriceResponse eventAndSumPriceResponse = Mapper
                .eventAndSumPriceResponseFromEventPlace(event, place);
        eventAndSumPriceResponse.setSumPrice(sumPrice);
        return eventAndSumPriceResponse;
    }

    @Override
    public Page<EventPlaceNameAndTicketsResponse> findAllEventsAndTickets(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        if (events.isEmpty() || events == null){
            throw new EventsNotFoundException(ErrorCode.EVENTS_NOT_FOUND);
        }
        return events.map(event -> Mapper.eventPlaceNameAndTicketsMapperFromEvent(event));
    }

    @Override
    public Page<EventAndPlaces> findEventsByDate(LocalDateTime datetime, Pageable pageable) {

        Page<Event> events = eventRepository.findEventsByEventDate(datetime, pageable);
        return events.map(event -> Mapper.eventAndPlacesMapper(event));

    }


    @Override
    public EventResponseDTO updateEventDescription(Long eventId, String description) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND));
        event.setDescription(description);
        Event savedEvent = eventRepository.save(event);
        return Mapper.eventResponseMapper(savedEvent);

    }


}
