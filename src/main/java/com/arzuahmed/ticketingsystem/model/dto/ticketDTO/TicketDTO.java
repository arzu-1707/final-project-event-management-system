package com.arzuahmed.ticketingsystem.model.dto.ticketDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private LocalDateTime purchaseDate;

}
