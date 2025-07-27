package com.arzuahmed.ticketingsystem.model.dto.placeDTO;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String placeName;

    @NotNull
    @NotBlank
    @NotEmpty
    private String location;

    //sayi yazmaq
    private Integer seatCapacity;
}
