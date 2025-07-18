package com.arzuahmed.ticketingsystem.model.dto.eventDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private String name;

    private String description;

    private Long placeId;

    @Column(unique = true)
    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private Boolean availableTickets;
}
