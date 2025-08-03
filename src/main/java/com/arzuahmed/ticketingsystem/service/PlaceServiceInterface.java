package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceWithEventsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceServiceInterface {

    PlaceResponse createPlace(PlaceDTO placeDTO);

    PlaceResponse findPlaceById(Long placeId);

    Page<PlaceResponse> findPlaceByName(String placeName, Pageable pageable);

    Page<PlaceResponse> findAllPlaces(Pageable pageable);

    Place findPlaceByNameAndLocation(String placeName, String location);

    void deletePlaceById(Long placeId);

    PlaceWithEventsResponse findEventsByPlaceId(Long placeId);

    PlaceWithEventsResponse findEventsByPlaceName(String placeName);

    //  TicketType addTicketType(TicketTypeDTO ticketTypeDTO);
}
