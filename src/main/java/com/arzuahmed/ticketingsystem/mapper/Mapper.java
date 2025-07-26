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
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import com.arzuahmed.ticketingsystem.model.response.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.TicketTypeResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventPlaceIdWithTicketsDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
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
                .availableTickets(eventDTO.getMaxTickets())
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
        TicketType ticketType = new TicketType();
        ticketType.setTicketTypeName(TICKETTYPENAME.REGULAR);
      return Ticket.builder()
                .ticketType(ticketType)
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

//    public static EventResponseDTO eventResponseMapper(Event event){
//        List<TicketResponseDTO> list = ticketDTOMapper(event);
//
//        PlaceDTO placeDTO = PlaceDTO.builder()
//                .placeName(event.getPlace().getPlaceName())
//                .seatCapacity(event.getPlace().getSeatCapacity())
//                .build();
//
//        return EventResponseDTO.builder()
//                .eventName(event.getName())
//                .description(event.getDescription())
//                .eventDate(event.getEventDate())
//                .maxTickets(event.getMaxTickets())
//                .place(placeDTO)
//                .availableTickets(event.getAvailableTickets())
//                .eventId(event.getId())
//                .tickets(list)
//                .build();
//    }
//    public static PlaceDTO placeResponseDTOMapper(Place place){
//        return PlaceDTO.builder()
//                .placeName(place.getPlaceName())
//                .location(place.getLocation())
//                .seatCapacity(place.getSeatCapacity())
//                .build();
//    }

    public static List<TicketResponseDTO> ticketDTOMapper(Event event){
        return  event.getTickets().stream()
                .map(ticket -> TicketResponseDTO.builder()
                        .ticketNo(ticket.getTicketNo())
                        .ticketTypeName(
                                ticket.getTicketType() != null ? ticket.getTicketType().getTicketTypeName() : null
                        )
                        .status(ticket.getStatus()).build())
                .toList();
    }

//    public static EventResponseDTO eventResponseDTOMapper(Event event){
//        return  EventResponseDTO.builder()
//                .availableTickets(event.getAvailableTickets())
//                .eventName(event.getName())
//                .description(event.getDescription())
//                .maxTickets(event.getMaxTickets())
//                .place(placeResponseDTOMapper(event.getPlace()))
//                .eventDate(event.getEventDate())
//                .tickets(ticketDTOMapper(event))
//                .build();
//
//    }

    public static EventResponseDTO eventResponseMapper(Event event) {
        return EventResponseDTO.builder()
                .name(event.getName())
                .eventDate(event.getEventDate())
                .availableTickets(event.getAvailableTickets())
                .maxTickets(event.getMaxTickets())
                .description(event.getDescription())
                .build();
    }

    public static EventAndPlaceResponseDTO eventAndPlaceResponseDTO(Event savedEvent) {
        return EventAndPlaceResponseDTO.builder()
                .name(savedEvent.getName())
                .description(savedEvent.getDescription())
                .availableTickets(savedEvent.getAvailableTickets())
                .maxTickets(savedEvent.getMaxTickets())
                .eventDate(savedEvent.getEventDate())
                .placeId(savedEvent.getPlace().getId())
                .build();
    }

    public static List<TicketResponseDTO> ticketResponseListMapper(List<Ticket> tickets){
      return tickets.stream()
               .map(ticket -> TicketResponseDTO.builder()
                       .status(ticket.getStatus())
                       .ticketTypeName(ticket.getTicketType().getTicketTypeName())
                       .ticketNo(ticket.getTicketNo())
                       .build())
               .toList();
    }

    public static EventPlaceIdWithTicketsDTO eventPlaceIdWithTicketsMapper(Event savedEvent) {
        return EventPlaceIdWithTicketsDTO.builder()
                .name(savedEvent.getName())
                .description(savedEvent.getDescription())
                .eventDate(savedEvent.getEventDate())
                .maxTickets(savedEvent.getMaxTickets())
                .placeId(savedEvent.getPlace().getId())
                .ticketResponse(ticketResponseListMapper(savedEvent.getTickets()))
                .build();

    }

    public static List<TicketTypeResponseDTO> ticketDTOMapper(List<Ticket> ticketList){
      return ticketList.stream()
               .map(ticket -> TicketTypeResponseDTO.builder()
                       .price(ticket.getTicketType().getPrice())
                       .ticketTypeName(ticket.getTicketType().getTicketTypeName()).build())
               .toList();
    }

    public static EventAndTicketsResponseDTO eventAndTicketsResponseDTOMapper(Event savedEvent) {
        return EventAndTicketsResponseDTO.builder()
                .name(savedEvent.getName())
                .description(savedEvent.getDescription())
                .maxTickets(savedEvent.getMaxTickets())
                .eventDate(savedEvent.getEventDate())
                .availableTickets(savedEvent.getAvailableTickets())
                .tickets(ticketDTOMapper(savedEvent.getTickets()))
                .build();
    }
    /*public static EventResponseDTO eventResponseWithTicketTypeListMapper(Event event){
        return EventResponseDTO.builder()
                .maxTickets(event.getMaxTickets())
                .eventDate(event.getEventDate())
                .description(event.getDescription())
                .eventName(event.getName())
                .availableTickets(event.getAvailableTickets())
                .tickets(event.getTickets())
    }*/
}
