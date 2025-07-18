package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventExistsException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.repository.EventRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.EventServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService implements EventServiceInterface {

    private final EventRepositoryInterface eventRepository;
    private final PlaceService placeService;

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
    public Event createEvent(EventDTO eventDTO) {
        //eyni tarixe cox event elave etmemek ucun bunu yoxlayiram
        if (eventRepository.existsEventsByEventDate(eventDTO.getEventDate())){
            throw new EventExistsException("Event is already exists");
        }
        Place place = placeService.findPlaceById(eventDTO.getPlaceId());
        Event event = Mapper.eventMapper(eventDTO);
        event.setPlace(place);
        place.addEvent(event);
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = findEventById(eventId);
        eventRepository.delete(existedEvent(event));
    }

    @Override
    public List<Event> findAllEvents() {
        List<Event> allUsers = eventRepository.findAll();
        if (allUsers.isEmpty()){
            throw new EventsNotFoundException("Events not found");
        }
        return allUsers;
    }

    @Override
    public List<Event> getEventByName(String eventName) {
        List<Event> events = eventRepository.findEventByNameIsLikeIgnoreCase(eventName);
        if(events.isEmpty()){
            throw new EventsNotFoundException("Events not found");
        }
        return events;
    }


    public Event existedEvent(Event event){
        if (event == null) {
            throw new EventNotFoundException("Event is not found");
        }
        return event;
    }
}
