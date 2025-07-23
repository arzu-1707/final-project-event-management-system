package com.arzuahmed.ticketingsystem.mapper;


import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.AddTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketCreateDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.*;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.response.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.TicketResponseDTO;
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

    public static Ticket ticketMapper(TicketCreateDTO ticketCreateDTO){
        return Ticket.builder()
                .ticketNo(ticketCreateDTO.getTicketCount())
                .build();
    }

    public static List<Ticket> ticketsMapper(AddTicketsDTO ticketsDTOS){
        List<Ticket> tickets = new ArrayList<>();
        for (TicketCreateDTO ticketCreateDTO : ticketsDTOS.getTickets()){
            Ticket ticket = ticketMapper(ticketCreateDTO);
            tickets.add(ticket);
        }
        return tickets;
    }

    public static EventResponseDTO eventResponseMapper(Event event){
        List<TicketResponseDTO> list = event.getTickets().stream()
                .map(ticket -> TicketResponseDTO.builder()
                        .ticketNo(ticket.getTicketNo())
                        .ticketTypeName(
                                ticket.getTicketType() != null ? ticket.getTicketType().getTicketTypeName() : null
                        )
                        .id(ticket.getId())
                        .status(ticket.getStatus()).build())
                .toList();
        return EventResponseDTO.builder()
                .eventName(event.getName())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .maxTickets(event.getMaxTickets())
                .place(event.getPlace())
                .availableTickets(event.getAvailableTickets())
                .eventId(event.getId())
                .tickets(list)
                .build();
    }

}
