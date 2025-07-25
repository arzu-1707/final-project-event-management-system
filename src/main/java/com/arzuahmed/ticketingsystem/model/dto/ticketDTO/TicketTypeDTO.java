package com.arzuahmed.ticketingsystem.model.dto.ticketDTO;

import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeDTO {


    private TICKETTYPENAME ticketTypeName;

    private Double price;

    private Integer ticketCount;


}
