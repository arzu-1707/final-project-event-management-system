package com.arzuahmed.ticketingsystem.model.dto;

import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Integer ticketNo;
    private EventDTO event;
    private TicketTypeDTO ticketType;
    private LocalDateTime purchaseDate;
    private STATUS status;
}
