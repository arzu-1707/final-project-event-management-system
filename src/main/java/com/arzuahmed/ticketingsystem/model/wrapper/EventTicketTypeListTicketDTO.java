package com.arzuahmed.ticketingsystem.model.wrapper;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTicketTypeListTicketDTO {
    private EventWithPlaceIdDTO eventDTO;
    private TicketDTO ticketDTO;
    private List<TicketTypeDTO> ticketTypeDTOS;
}
