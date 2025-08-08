package com.arzuahmed.ticketingsystem.model.response.ticketResponse;

import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponseDTO {
    private Integer ticketNo;
    private STATUS status;
    private TICKETTYPENAME ticketTypeName;
    private Double price;
}
