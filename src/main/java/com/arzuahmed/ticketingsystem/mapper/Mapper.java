package com.arzuahmed.ticketingsystem.mapper;


import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.service.impl.PlaceService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.net.PasswordAuthentication;

@Data
public class Mapper {

    //burada gorulecek isler:
    //Sonda kodu bcript formada saxla


    public static User userMapper(UserDTO userDTO){
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public static Event eventMapper(EventDTO eventDTO) {
        return Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .eventDate(eventDTO.getEventDate())
                .availableTickets(eventDTO.getAvailableTickets())
                .maxTickets(eventDTO.getMaxTickets())
                .build();
    }

    public static Place placeMapper(PlaceDTO placeDTO){
        return Place.builder()
                .placeName(placeDTO.getPlaceName())
                .location(placeDTO.getLocation())
                .seatCapacity(placeDTO.getSeatCapacity())
                .build();
    }

}
