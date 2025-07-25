package com.arzuahmed.ticketingsystem.model.response;

import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {
    private Long eventId;
    private String eventName;
    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy  HH:mm")
    private LocalDateTime eventDate;
    private PlaceDTO place;
    private Integer maxTickets;
    private Integer availableTickets;

    private List<TicketResponseDTO> tickets;

}
