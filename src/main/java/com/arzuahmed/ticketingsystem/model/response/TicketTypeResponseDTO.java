package com.arzuahmed.ticketingsystem.model.response;

import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeResponseDTO {

    private TICKETTYPENAME ticketTypeName;

    private Double price;
}
