package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;

import java.util.List;

public interface PlaceServiceInterface {

    Place createPlace(PlaceDTO placeDTO);

    Place findPlaceById(Long placeId);

    List<Place> findPlaceByName(String placeName);

    List<Place> findAllPlaces();

    List<Event> findEventsByPlaceId(Long placeId);
}
