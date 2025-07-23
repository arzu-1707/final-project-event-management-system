package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventExistsException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceNotFoundException;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.AddTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketCreateDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.wrapper.EventTicketTypeListTicketDTO;
import com.arzuahmed.ticketingsystem.model.wrapper.TicketAndTicketTypeDTO;
import com.arzuahmed.ticketingsystem.repository.EventRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.TicketTypeRepository;
import com.arzuahmed.ticketingsystem.service.EventServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Event findEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()-> new EventNotFoundException("Event is not found"));
    }

    @Override
    public Event findEventByName(String eventName) {
        Event event = eventRepository.findByName(eventName);
        return existedEvent(event);
    }

    @Override
    @Transactional
    public Event createEventWithTickets(EventWithPlaceIdDTO eventDTO) {
        //eyni tarixe cox event elave etmemek ucun bunu yoxlayiram
        if (eventRepository.existsEventsByEventDate(eventDTO.getEventDate())){
            throw new EventExistsException("Event is already exists");
        }

        Place place = placeService.findPlaceById(eventDTO.getPlaceId());

        Event event = Mapper.eventMapper(eventDTO);
        event.setPlace(place);
        place.addEvent(event);

        Event savedEvent = eventRepository.save(event);
        List<Ticket> tickets = savedEvent.addTickets();

        ticketService.saveAll(tickets);

        return savedEvent;
    }

    @Override
    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = findEventById(eventId);
        Place place = event.getPlace();
        if (place != null){
        place.removeEvent(event);}
        eventRepository.delete(existedEvent(event));
    }

    @Override
    public List<Event> findAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        if (allEvents.isEmpty()){
            throw new EventsNotFoundException("Events not found");
        }
        return allEvents;
    }

    @Override
    public List<Event> getEventByName(String eventName) {
        List<Event> events = eventRepository.findEventByNameIsLikeIgnoreCase(eventName);
        if(events.isEmpty()){
            throw new EventsNotFoundException("Events not found");
        }
        return events;
    }

    @Override
    @Transactional
    public Event createEventTicketWithTicketType(EventTicketTicketTypeDTO eventTicketTicketType) {
        Place place = placeService.findPlaceById(eventTicketTicketType.getEventDTO().getPlaceId());
        Event event = Mapper.eventMapper(eventTicketTicketType.getEventDTO());
        event.setPlace(place);
        place.addEvent(event);
        Event savedEvent = eventRepository.save(event);

        TicketType ticketType = Mapper.ticketTypeMapper(eventTicketTicketType.getTicketTypeDTO());
        ticketType.setEvent(savedEvent);
        ticketTypeRepository.save(ticketType);


        List<Ticket> tickets = new ArrayList<>();
        int ticketCounter = 1;
        for (int i = 1; i <= eventTicketTicketType.getTicketTypeDTO().getTicketCount(); i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketNo(ticketCounter++);
            ticket.setEvent(savedEvent);
            ticket.setStatus(STATUS.AVAILABLE);
            ticket.setTicketType(ticketType);
            tickets.add(ticket);
        }
         ticketService.saveAll(tickets);
        savedEvent.setTickets(tickets);
         return savedEvent;
    }

    @Override
    @Transactional
    public Event createEventTicketWithTicketType(EventTicketTypeListTicketDTO eventTicketTypeListTicket) {
        Place place
                = placeService.findPlaceById(eventTicketTypeListTicket.getEventDTO().getPlaceId());

        Event event = Mapper.eventMapper(eventTicketTypeListTicket.getEventDTO());

        event.setPlace(place);
        place.addEvent(event);
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
                ticket.setPurchaseDate(eventTicketTypeListTicket.getTicketDTO().getPurchaseDate());
                ticket.setTicketNo(ticketCounter++);
                ticket.setEvent(savedEvent);
                ticket.setTicketType(ticketType);
                ticket.setStatus(STATUS.AVAILABLE);
                tickets.add(ticket);
            }
        }
        ticketService.saveAll(tickets);
        savedEvent.setTickets(tickets);
        return savedEvent;
    }


    @Override
    public Event existedEvent(Event event){
        if (event == null) {
            throw new EventNotFoundException("Event is not found");
        }
        return event;
    }

    @Override
    public Event existedEventById(Long eventId){
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new EventNotFoundException("Event is not found");
        }
        return event;
    }

    @Override
    public Event createEvent(EventDTO eventDTO) {
        Event event = Mapper.eventMapper(eventDTO);
       if (eventRepository.existsEventsByEventDate(eventDTO.getEventDate())){
           throw new EventExistsException("Event already exists at this date and time: "
                   + eventDTO.getEventDate());
        }
        return eventRepository.save(event);
    }

    @Override
    public Event addPlaceInEvent(Long eventId, Long placeId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event is not found"));
        Place place = placeService.findPlaceById(placeId);
        if (place==null){
            throw  new PlaceNotFoundException("Place is not found");
        }
        event.setPlace(place);
        place.addEvent(event);
        return eventRepository.save(event);
    }

    @Override
    public Event addTicketsInEvent(Long eventId, AddTicketsDTO ticketsDTO) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event is not found"));

       // List<Ticket> tickets = Mapper.ticketsMapper(ticketsDTO);
        List<Ticket> tickets = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < ticketsDTO.getTickets().size(); i++) {
            TicketCreateDTO ticketCreateDTO = ticketsDTO.getTickets().get(i);
            TicketType ticketType = ticketTypeService.findTicketTypeById(ticketCreateDTO.getTicketTypeId());
            for (int j = 1; j <= ticketCreateDTO.getTicketCount() ; j++) {
                Ticket ticket = new Ticket();
                ticket.setTicketNo(counter++);
                ticket.setTicketType(ticketType);
                ticket.setStatus(STATUS.AVAILABLE);
                ticket.setEvent(event);
                tickets.add(ticket);
            }
        }
        event.setTickets(tickets);
        ticketService.saveAll(tickets);

        return eventRepository.save(event);
    }

    @Override
    public Event addTicketType(Long eventId, List<TicketTypeDTO> ticketTypeDTO) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event is not found"));
        List<Ticket> tickets = new ArrayList<>();

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

        return eventRepository.save(event);

    }

    @Override
    public Event updatePlace(Long eventId, PlaceDTO placeDTO) {
        Event event = eventRepository.findById(eventId).orElseThrow(()->
                new EventNotFoundException("Event is not found"));
      Place place = placeService.findPlaceByNameAndLocation(placeDTO.getPlaceName(), placeDTO.getLocation());
      if (place == null){
          place = placeService.createPlace(placeDTO);
      }
        event.setPlace(place);
        place.addEvent(event);
       return eventRepository.save(event);

    }

    @Override
    public List<Event> findEventsByPlaceId(Long placeId){
        Place place = placeService.findPlaceById(placeId);
        List<Event> events = place.getEvents();
        if (events.isEmpty()){
            throw new EventsNotFoundException("events not found");
        }
        return events;
    }

    @Override
    public Event updateEventDate(Long eventId, EventDateDTO eventDateDTO) {
        Event event = findEventById(eventId);
        event.setEventDate(eventDateDTO.getEventDate());
        return eventRepository.save(event);
    }


}
