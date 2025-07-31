package com.arzuahmed.ticketingsystem.service.impl;


import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventExistsException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceNotFoundException;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventAndPlaces;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTypeListTicketDTO;
import com.arzuahmed.ticketingsystem.repository.EventRepositoryInterface;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    @PreAuthorize("hasRole('ADMIN')")
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
    public EventAndTicketsResponseDTO createEventTicketWithTicketType(EventTicketTicketTypeDTO eventTicketTicketType) {

        Place place = placeService.findById(eventTicketTicketType.getEventDTO().getPlaceId());
        Event event = Mapper.eventMapper(eventTicketTicketType.getEventDTO());
        event.setPlace(place);
        place.addEvent(event);

        Validate.validateMaxTickets(eventTicketTicketType.getEventDTO().getMaxTickets(), event.getPlace().getSeatCapacity());
        Validate.validateTicketCount(eventTicketTicketType.getTicketTypeDTO().getTicketCount(), eventTicketTicketType.getEventDTO().getMaxTickets());

        Event savedEvent = eventRepository.save(event);



        TicketType ticketType = Mapper.ticketTypeMapper(eventTicketTicketType.getTicketTypeDTO());
        ticketType.setEvent(savedEvent);
        TicketType savedTicketType = ticketTypeRepository.save(ticketType);


        List<Ticket> tickets = new ArrayList<>();
        int ticketCounter = 1;
        for (int i = 1; i <= eventTicketTicketType.getTicketTypeDTO().getTicketCount(); i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketNo(ticketCounter++);
            ticket.setEvent(savedEvent);
            ticket.setStatus(STATUS.AVAILABLE);
            ticket.setTicketType(savedTicketType);
            tickets.add(ticket);
        }

         ticketService.saveAll(tickets);
         savedEvent.setTickets(tickets);

        return Mapper.eventAndTicketsResponseDTOMapper(savedEvent);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EventAndTicketsResponseDTO createEventTicketWithTicketType(EventTicketTypeListTicketDTO eventTicketTypeListTicket) {
        Place place
                = placeService.findById(eventTicketTypeListTicket.getEventDTO().getPlaceId());

        Event event = Mapper.eventMapper(eventTicketTypeListTicket.getEventDTO());

        event.setPlace(place);
        place.addEvent(event);

        Validate.validateMaxTickets(eventTicketTypeListTicket.getEventDTO().getMaxTickets(), event.getPlace().getSeatCapacity());
        Validate.validateTicketCountsFromTypeDTO(eventTicketTypeListTicket.getTicketTypeDTOS(), eventTicketTypeListTicket.getEventDTO().getMaxTickets());

        Event savedEvent = eventRepository.save(event);


        List<TicketType> ticketTypeList = Mapper.ticketTypesMapper(eventTicketTypeListTicket.getTicketTypeDTOS());
        for (TicketType ticketType: ticketTypeList){
            ticketType.setEvent(event);
            ticketTypeRepository.save(ticketType);
        }


        List<Ticket> tickets = new ArrayList<>();
        int ticketCounter = 1;
        for (int i=0; i<eventTicketTypeListTicket.getTicketTypeDTOS().size(); i++){

            TicketTypeDTO ticketTypeDTO = eventTicketTypeListTicket.getTicketTypeDTOS().get(i);
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

       return Mapper.eventAndTicketsResponseDTOMapper(savedEvent);
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
    @PreAuthorize("hasRole('ADMIN')")
    public Event existedEventById(Long eventId){
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND);
        }
        return event;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponseDTO createEvent(EventDTO eventDTO) {
        Event event = Mapper.eventMapper(eventDTO);
       if (eventRepository.existsEventsByEventDate(eventDTO.getEventDate())){
           throw new EventExistsException(ErrorCode.EVENT_ALREADY_EXITS);
        }
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
            new PlaceNotFoundException(ErrorCode.PLACE_NOT_FOUND);
        }
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

        Validate.validateTicketCountsFromTypeDTO(ticketTypeDTO, event.getMaxTickets());
        int counter = 1;
       for(TicketTypeDTO ticketType : ticketTypeDTO){
           TicketType ticketType1 = Mapper.ticketTypeMapper(ticketType);
           ticketType1 = ticketTypeRepository.save(ticketType1);
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

       return Mapper.eventAndTicketsResponseDTOMapper(savedEvent);

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


}
