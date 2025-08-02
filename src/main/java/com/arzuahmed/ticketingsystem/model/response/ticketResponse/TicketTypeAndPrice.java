package com.arzuahmed.ticketingsystem.model.response.ticketResponse;

import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeAndPrice {

    private TICKETTYPENAME tickettypename;

    private Double price;

    private Integer count;
}
