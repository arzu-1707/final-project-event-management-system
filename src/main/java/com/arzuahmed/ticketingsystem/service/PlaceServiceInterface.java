package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;

import java.util.List;

public interface PlaceServiceInterface {

    Place createPlace(PlaceDTO placeDTO);

    Place findPlaceById(Long placeId);

    List<Place> findPlaceByName(String placeName);

    List<Place> findAllPlaces();

    Place findPlaceByNameAndLocation(String placeName, String location);

    void deletePlaceById(Long placeId);

  //  TicketType addTicketType(TicketTypeDTO ticketTypeDTO);
}
