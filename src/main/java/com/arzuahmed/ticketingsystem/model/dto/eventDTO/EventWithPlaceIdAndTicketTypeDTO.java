package com.arzuahmed.ticketingsystem.model.dto.eventDTO;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventWithPlaceIdAndTicketTypeDTO {

    @NotBlank
    @NotNull
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    private Long placeId;


    @Column(unique = true)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private TicketTypeDTO ticketTypeDTO;


}
