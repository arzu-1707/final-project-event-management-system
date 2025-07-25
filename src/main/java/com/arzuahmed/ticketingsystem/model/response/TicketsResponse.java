package com.arzuahmed.ticketingsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketsResponse {

        private String message;
        private List<Integer> TicketNo;
}
