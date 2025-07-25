package com.arzuahmed.ticketingsystem.service.impl;

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
import com.arzuahmed.ticketingsystem.repository.PlaceRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.TicketTypeRepository;
import com.arzuahmed.ticketingsystem.service.PlaceServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService implements PlaceServiceInterface {

    private final PlaceRepositoryInterface placeRepository;
    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public Place createPlace(PlaceDTO placeDTO) {
        Place place = Mapper.placeMapper(placeDTO);
        if (placeRepository.existsByPlaceNameEqualsIgnoreCase(place.getPlaceName())){
            throw new PlaceAlreadyExistsException("A place with this name already exists.");
        }
        return placeRepository.save(place);
    }

    @Override
    public Place findPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(()-> new PlaceNotFoundException("Place is not found"));
    }

    @Override
    public List<Place> findPlaceByName(String placeName) {
        List<Place> places = placeRepository.findPlaceByPlaceNameEqualsIgnoreCase(placeName);
        if (places.isEmpty()){
            throw new PlacesNotFoundException("Places are not found");
        }
        return places;
    }

    @Override
    public List<Place> findAllPlaces() {
        List<Place> allPlaces = placeRepository.findAll();
        if (allPlaces.isEmpty()){
            throw new PlacesNotFoundException("Places not found");
        }
        return allPlaces;
    }


    @Override
    public Place findPlaceByNameAndLocation(String placeName, String location) {
        return placeRepository.findPlaceByPlaceNameAndLocation(placeName, location)
                .stream().findFirst().orElseThrow(() -> new PlaceNotFoundException("Place is not found"));
    }

    @Override
    public void deletePlaceById(Long placeId)
    {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException("Place is not found"));
        placeRepository.delete(place);
    }}

//    @Override
//    public TicketType addTicketType(TicketTypeDTO ticketTypeDTO) {
//        TicketType ticketType = Mapper.ticketTypeMapper(ticketTypeDTO);
//        if (ticketTypeRepository.existsByTicketTypeNameAndPrice(ticketType.getTicketTypeName(), ticketType.getPrice())){
//           throw new TicketTypeAlreadyExistException("Bu TicketType artiq bazada movcuddur..");
//        }
//       return ticketTypeRepository.save(ticketType);
//    }
//}
