package com.arzuahmed.ticketingsystem.model.dto.placeDTO;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {

    @Column(unique = true)
    private String placeName;

    private String location;
    //sayi yazmaq
    private Integer seatCapacity;
}
