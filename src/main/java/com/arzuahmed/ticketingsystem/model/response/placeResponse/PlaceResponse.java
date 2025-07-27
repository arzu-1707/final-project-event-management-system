package com.arzuahmed.ticketingsystem.model.response.placeResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceResponse {

    private Long id;

    private String placeName;

    private String location;

    private Integer seatCapacity;
}
