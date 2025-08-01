package com.arzuahmed.ticketingsystem.model.wrapper;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketAndTicketTypeDTO {
    private TicketDTO ticketDTO;
    private List<TicketTypeDTO> ticketTypeDTO;
}
