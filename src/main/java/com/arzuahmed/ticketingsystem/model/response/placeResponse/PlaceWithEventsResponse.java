package com.arzuahmed.ticketingsystem.model.response.placeResponse;

import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceWithEventsResponse {
    private Long id;

    private String placeName;

    private String location;

    private Integer seatCapacity;

    private List<EventResponseDTO> eventResponseDTO;
}
