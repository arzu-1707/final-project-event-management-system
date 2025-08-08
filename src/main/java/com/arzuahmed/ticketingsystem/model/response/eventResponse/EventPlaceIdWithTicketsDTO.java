package com.arzuahmed.ticketingsystem.model.response.eventResponse;

import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class EventPlaceIdWithTicketsDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    private String description;

    private Long placeId;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private Integer availableTickets;

    private List<TicketResponseDTO> ticketResponse;
}
