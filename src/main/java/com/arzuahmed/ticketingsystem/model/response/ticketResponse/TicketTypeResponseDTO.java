package com.arzuahmed.ticketingsystem.model.response.ticketResponse;

import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Stack;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeResponseDTO {
    private Integer ticketNo;

    private TICKETTYPENAME ticketTypeName;

    private Double price;

    private STATUS status;

}
