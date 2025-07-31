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
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceWithEventsResponse;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketTypeResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventPlaceIdWithTicketsDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.roleResponse.RoleResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventAndPlaces;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Mapper {

    //burada gorulecek isler:
    //Sonda kodu bcript formada saxla


    public static User userMapper(UserDTO userDTO){
        return User.builder()
                .userName(userDTO.getName())
                .email(userDTO.getEmail())
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

    public static List<TicketResponseDTO> ticketResponseFromEventMapper(Event event){
        return  event.getTickets().stream()
                .map(ticket -> TicketResponseDTO.builder()
                        .ticketNo(ticket.getTicketNo())
                        .ticketTypeName(
                                ticket.getTicketType() != null ? ticket.getTicketType().getTicketTypeName() : null
                        )
                        .status(ticket.getStatus()).build())
                .toList();
    }
    public static List<TicketResponseDTO> ticketResponseFromUserMapper(User user){
      return user.getTickets()
                .stream()
                .map(ticket-> TicketResponseDTO.builder()
                        .ticketNo(ticket.getTicketNo())
                        .ticketTypeName(ticket.getTicketType().getTicketTypeName())
                        .status(ticket.getStatus())
                        .build()).toList();
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
                .id(event.getId())
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

    public static Page<UserResponse> userResponseMapper(Page<User> allUsers) {
        return allUsers.map(user -> UserResponse
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .roles(roleResponseSetMapper(user.getRoles()))
                .build());
    }

    public static Set<RoleResponse> roleResponseSetMapper(Set<Role> roles){
        return roles.stream()
                .map(role-> RoleResponse
                        .builder().role(role.getRole())
                        .build()).collect(Collectors.toSet());
    }

    public static UserResponse userResponseMapper(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .roles(roleResponseSetMapper(user.getRoles()))
                .build();
    }

    public static PlaceResponse placeResponseMapper(Place savedPlace) {
        return PlaceResponse.builder()
                .id(savedPlace.getId())
                .placeName(savedPlace.getPlaceName())
                .location(savedPlace.getLocation())
                .seatCapacity(savedPlace.getSeatCapacity())
                .build();
    }

    public static Place placeMapper(PlaceResponse placeResponse) {
        return Place.builder()
                .placeName(placeResponse.getPlaceName())
                .seatCapacity(placeResponse.getSeatCapacity())
                .location(placeResponse.getLocation())
                .build();
    }

    public static Page<EventResponseDTO> eventResponsePageMapper(Page<Event> allEvents) {
        return allEvents.map(event ->
                EventResponseDTO.builder()
                        .id(event.getId())
                        .name(event.getName())
                        .maxTickets(event.getMaxTickets())
                        .availableTickets(event.getAvailableTickets())
                        .eventDate(event.getEventDate())
                        .description(event.getDescription())
                        .build());
    }

    public static Page<PlaceResponse> placeResponseMapper(Page<Place> places) {
        return places.map(place -> PlaceResponse.builder()
                .id(place.getId())
                .placeName(place.getPlaceName())
                .location(place.getLocation())
                .seatCapacity(place.getSeatCapacity())
                .location(place.getLocation()).build());

    }

    public static List<EventResponseDTO> eventResponseListMapper(List<Event> events){
       return events.stream()
                .map(event -> EventResponseDTO.builder()
                        .name(event.getName())
                        .id(event.getId())
                        .eventDate(event.getEventDate())
                        .maxTickets(event.getMaxTickets())
                        .availableTickets(event.getAvailableTickets())
                        .description(event.getDescription())
                        .build())
                .toList();
    }


    public static PlaceWithEventsResponse placeWithEventsResponse(Place place) {
        return PlaceWithEventsResponse
                .builder()
                .id(place.getId())
                .placeName(place.getPlaceName())
                .location(place.getLocation())
                .seatCapacity(place.getSeatCapacity())
                .eventResponseDTO(eventResponseListMapper(place.getEvents()))
                .build();
    }



    public static List<TicketResponseDTO> ticketResponseDTOMapper(List<Ticket> tickets) {
      return  tickets.stream().map(ticket -> TicketResponseDTO
                .builder()
                .ticketNo(ticket.getTicketNo())
                .status(ticket.getStatus())
                .ticketTypeName(ticket.getTicketType().getTicketTypeName())
                .build()).toList();
    }

    public static Place placeResponseMapper(PlaceResponse placeResponse){
        return Place.builder()
                .id(placeResponse.getId())
                .placeName(placeResponse.getPlaceName())
                .location(placeResponse.getLocation())
                .seatCapacity(placeResponse.getSeatCapacity())
                .build();
    }



    public static EventAndPlaces eventAndPlacesMapper(Event event) {
        return EventAndPlaces.builder()
                .name(event.getName())
                .description(event.getDescription())
                .eventId(event.getId())
                .maxTickets(event.getMaxTickets())
                .eventDate(event.getEventDate())
                .availableTickets(event.getAvailableTickets())
                .placeId(event.getPlace().getId())
                .placeName(event.getPlace().getPlaceName())
                .location(event.getPlace().getLocation())
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
