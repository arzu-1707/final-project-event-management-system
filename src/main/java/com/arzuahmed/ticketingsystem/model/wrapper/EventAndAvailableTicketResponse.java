package com.arzuahmed.ticketingsystem.model.wrapper;

import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketTypeResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class EventAndAvailableTicketResponse {
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm")
    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private Integer availableTickets;

    private List<TicketTypeResponseDTO> tickets;
}
