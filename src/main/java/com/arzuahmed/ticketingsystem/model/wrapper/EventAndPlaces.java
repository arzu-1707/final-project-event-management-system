package com.arzuahmed.ticketingsystem.model.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventAndPlaces {

    private Long eventId;

    @NotBlank
    @NotNull
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    @NotNull
    private String description;

    @Column(unique = true)
   @JsonFormat(pattern ="dd.MM.yyyy HH:mm")
    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private Integer availableTickets;

    private Long placeId;

    private String placeName;

    private String location;

}
