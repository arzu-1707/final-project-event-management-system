package com.arzuahmed.ticketingsystem.model.response.eventResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
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
public class EventAndPlaceResponseDTO {
    @NotNull
    private String name;

    @NotNull
    private String description;

    private Long placeId;

    @Future(message = "Event tarixi kecmisde ola bilmez...")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime eventDate ;

    private Integer  maxTickets;

    private Integer availableTickets;

}
