package com.arzuahmed.ticketingsystem.model.dto.eventDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventWithPlaceIdDTO {

    @NotBlank
    @NotNull
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    @NotEmpty
    @NotBlank
    private Long placeId;

    @Column(unique = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private Integer availableTickets;

}
