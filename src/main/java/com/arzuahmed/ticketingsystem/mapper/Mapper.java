package com.arzuahmed.ticketingsystem.mapper;


import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.*;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Mapper {

    //burada gorulecek isler:
    //Sonda kodu bcript formada saxla


    public static User userMapper(UserDTO userDTO){
        return User.builder()
                .userName(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public static Event eventMapper(EventWithPlaceIdDTO eventDTO) {
        return Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .eventDate(eventDTO.getEventDate())
                .availableTickets(eventDTO.getAvailableTickets())
                .maxTickets(eventDTO.getMaxTickets())
                .build();
    }

    public static Event eventMapper(EventDTO eventDTO){
        return Event.builder()
                .name(eventDTO.getName())
                .availableTickets(eventDTO.getAvailableTickets())
                .eventDate(eventDTO.getEventDate())
                .maxTickets(eventDTO.getMaxTickets())
                .description(eventDTO.getDescription())
                .build();
    }

    public static Place placeMapper(PlaceDTO placeDTO){
        return Place.builder()
                .placeName(placeDTO.getPlaceName())
                .location(placeDTO.getLocation())
                .seatCapacity(placeDTO.getSeatCapacity())
                .build();
    }

    public static Ticket ticketMapper(){
       return   Ticket.builder()
                .status(STATUS.AVAILABLE)
                .build();
    }

    public static TicketType ticketTypeMapper(TicketTypeDTO ticketTypeDTO){
        return TicketType.builder()
                .ticketTypeName(ticketTypeDTO.getTicketTypeName())
                .price(ticketTypeDTO.getPrice())
                .build();
    }

    public static List<TicketType> ticketTypesMapper(List<TicketTypeDTO> ticketTypesDTO){
        List<TicketType> ticketTypeList = new ArrayList<>();
        for (TicketTypeDTO ticketType: ticketTypesDTO){
            TicketType ticketType1 = ticketTypeMapper(ticketType);
            ticketTypeList.add(ticketType1);
        }
        return ticketTypeList;
    }

}
