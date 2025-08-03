package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceAlreadyExistsException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlacesNotFoundException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketNotFoundException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketTypeAlreadyExistException;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceWithEventsResponse;
import com.arzuahmed.ticketingsystem.repository.PlaceRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.TicketTypeRepository;
import com.arzuahmed.ticketingsystem.service.PlaceServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService implements PlaceServiceInterface {

    private final PlaceRepositoryInterface placeRepository;
    private final TicketTypeRepository ticketTypeRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PlaceResponse createPlace(PlaceDTO placeDTO) {
        Place place = Mapper.placeMapper(placeDTO);
        if (placeRepository.existsByPlaceNameEqualsIgnoreCase(place.getPlaceName())){
            throw new PlaceAlreadyExistsException(ErrorCode.PLACE_ALREADY_EXISTS_EXCEPTION);
        }
        Place savedPlace = placeRepository.save(place);
        return Mapper.placeResponseMapper(savedPlace);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PlaceResponse findPlaceById(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException(ErrorCode.PLACE_NOT_FOUND));
        return Mapper.placeResponseMapper(place);
    }

    @Override
    public Page<PlaceResponse> findPlaceByName(String placeName, Pageable pageable) {
        Page<Place> places = placeRepository.findAllByPlaceNameEqualsIgnoreCase(placeName, pageable);
        if (places.isEmpty()){
            throw new PlacesNotFoundException(ErrorCode.PLACES_NOT_FOUND);
        }
        return Mapper.placeResponseMapper(places);
    }

    @Override
    public Page<PlaceResponse> findAllPlaces(Pageable pageable) {
        Page<Place> allPlaces = placeRepository.findAll(pageable);
        if (allPlaces.isEmpty()){
            throw new PlacesNotFoundException(ErrorCode.PLACES_NOT_FOUND);
        }
       return Mapper.placeResponseMapper(allPlaces);
    }


    @Override
    public Place findPlaceByNameAndLocation(String placeName, String location) {
        return placeRepository.findPlaceByPlaceNameAndLocation(placeName, location);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePlaceById(Long placeId)
    {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException(ErrorCode.PLACE_NOT_FOUND));
        placeRepository.delete(place);
    }

    @Override
    public PlaceWithEventsResponse findEventsByPlaceId(Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() ->
                new PlaceNotFoundException(ErrorCode.PLACE_NOT_FOUND));

        if (place.getEvents() == null){
            throw new EventsNotFoundException(ErrorCode.EVENTS_NOT_FOUND);
        }

       return Mapper.placeWithEventsResponse(place);

    }

    @Override
    public PlaceWithEventsResponse findEventsByPlaceName(String placeName) {
        Place place = placeRepository.findByPlaceNameEqualsIgnoreCase(placeName).orElseThrow(
                () -> new PlaceNotFoundException(ErrorCode.PLACE_NOT_FOUND)
        );

        if (place.getEvents().isEmpty())
        {
            throw new EventsNotFoundException(ErrorCode.EVENTS_NOT_FOUND);
        }

       return Mapper.placeWithEventsResponse(place);
    }


    public Place findById(Long placeId){
       return placeRepository.findById(placeId).orElseThrow(()->
                new PlaceNotFoundException(ErrorCode.PLACE_NOT_FOUND));
    }



}

