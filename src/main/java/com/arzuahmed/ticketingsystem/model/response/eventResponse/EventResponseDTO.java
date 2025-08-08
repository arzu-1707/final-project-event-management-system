package com.arzuahmed.ticketingsystem.model.response.eventResponse;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {

    private Long id;
    private String name;

    private String description;


    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private Integer availableTickets;
}
