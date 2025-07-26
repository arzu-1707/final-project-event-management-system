package com.arzuahmed.ticketingsystem.model.response.eventResponse;

import com.arzuahmed.ticketingsystem.model.response.TicketTypeResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventAndTicketsResponseDTO {
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
