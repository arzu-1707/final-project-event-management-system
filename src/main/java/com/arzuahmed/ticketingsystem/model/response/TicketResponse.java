package com.arzuahmed.ticketingsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private String message;
    private Integer TicketNo;
}
