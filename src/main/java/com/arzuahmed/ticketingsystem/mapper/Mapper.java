package com.arzuahmed.ticketingsystem.mapper;


import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.AddTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketCreateDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdAndTicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserDTO;
import com.arzuahmed.ticketingsystem.model.entity.*;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.*;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceWithEventsResponse;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketTypeResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.roleResponse.RoleResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserInfoResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventAndPlaces;
import com.arzuahmed.ticketingsystem.model.wrapper.EventWithPlaceIdAndTicketTypeListDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Mapper {


    public static User userMapper(UserDTO userDTO){
        return User.builder()
                .userName(userDTO.getName())
                .email(userDTO.getEmail())
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

    public static TicketType ticketTypeMapperFromDTO(TicketTypeDTO ticketTypeDTO){
        return TicketType.builder()
                .ticketTypeName(ticketTypeDTO.getTicketTypeName())
                .price(ticketTypeDTO.getPrice())
                .build();
    }

    public static List<TicketType> ticketTypeListMapperFromTicketTypeDTO(List<TicketTypeDTO> ticketTypesDTO){
        return ticketTypesDTO.stream()
                .map(ticketType-> TicketType.builder()
                        .ticketTypeName(ticketType.getTicketTypeName())
                        .price(ticketType.getPrice())
                        .build()).toList();
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
                       .ticketTypeName(
                               ticket.getTicketType() != null ? ticket.getTicketType().getTicketTypeName() : null
                       )
                       .ticketNo(ticket.getTicketNo())
                       .price(ticket.getTicketType().getPrice())
                       .build())
               .toList();
    }



    public static List<TicketTypeResponseDTO> ticketDTOMapper(List<Ticket> ticketList){
      return ticketList.stream()
               .map(ticket -> TicketTypeResponseDTO.builder()
                       .ticketNo(ticket.getTicketNo())
                       .price(ticket.getTicketType().getPrice())
                       .status(ticket.getStatus())
                       .ticketTypeName(ticket.getTicketType().getTicketTypeName()).build())
               .toList();
    }

    public static EventAndTicketsResponseDTO eventAndTicketsResponseDTOMapperFromEvent(Event savedEvent) {
        return EventAndTicketsResponseDTO.builder()
                .name(savedEvent.getName())
                .description(savedEvent.getDescription())
                .tickets(tickeTypeResponseMapper(savedEvent.getTickets()))
                .maxTickets(savedEvent.getMaxTickets())
                .eventDate(savedEvent.getEventDate())
                .availableTickets(savedEvent.getAvailableTickets())
                .tickets(ticketDTOMapper(savedEvent.getTickets()))
                .build();
    }

    private static List<TicketTypeResponseDTO> tickeTypeResponseMapper(List<Ticket> tickets) {
      return tickets.stream()
                .map(ticket -> TicketTypeResponseDTO.builder()
                        .ticketNo(ticket.getTicketNo())
                        .ticketTypeName(ticket.getTicketType().getTicketTypeName())
                        .price(ticket.getTicketType().getPrice()).build())
              .toList();

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



    public static Event eventMapperFromEventWithPlaceIdAndTicketTypeDTO(EventWithPlaceIdAndTicketTypeDTO fullEvent) {
        return Event.builder()
                .eventDate(fullEvent.getEventDate())
                .name(fullEvent.getName())
                .description(fullEvent.getDescription())
                .maxTickets(fullEvent.getMaxTickets())
                .build();
    }

    public static Event eventMapperFromEventWithPlaceIdAndTicketTypeListDTO(EventWithPlaceIdAndTicketTypeListDTO fullEvent) {

        return Event
                .builder()
                .name(fullEvent.getName())
                .description(fullEvent.getDescription())
                .eventDate(fullEvent.getEventDate())
                .availableTickets(fullEvent.getMaxTickets())
                .maxTickets(fullEvent.getMaxTickets())
                .ticketTypes(ticketTypeListMapperFromTicketTypeDTO(fullEvent.getTicketTypeDTO()))
                .build();

    }

    public static EventAndSumPriceResponse eventAndSumPriceResponseFromEventPlace(Event event, Place place) {
        return EventAndSumPriceResponse
                .builder()
                .eventName(event.getName())
                .eventId(event.getId())
                .eventDate(event.getEventDate())
                .placeName(place.getPlaceName())
                .location(place.getLocation())
                .build();
    }

    public static EventPlaceNameAndTicketsResponse eventPlaceNameAndTicketsMapperFromEvent(Event event) {
        return EventPlaceNameAndTicketsResponse
                .builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .maxTickets(event.getMaxTickets())
                .availableTickets(event.getAvailableTickets())
                .tickets(ticketDTOMapper(event.getTickets()))
                .placeName(event.getPlace().getPlaceName())
                .location(event.getPlace().getLocation())
                .build();
    }

    public Set<RoleResponse> roleResponseMapperFromRole(Set<Role> role){
        return role.stream().map(role1-> RoleResponse.builder()
                .role(role1.getRole()).build())
                .collect(Collectors.toSet());
    }

    public static Page<UserInfoResponse> userInfoResponseMapper(Page<User> allUsers) {
       return allUsers.map(user -> UserInfoResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .roles(roleResponseSetMapper(user.getRoles()))
                .email(user.getEmail())
                .build());

    }
}
