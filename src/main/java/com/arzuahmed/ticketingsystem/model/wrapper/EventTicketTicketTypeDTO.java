package com.arzuahmed.ticketingsystem.model.wrapper;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventWithPlaceIdAndTicketTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTicketTicketTypeDTO {
    private EventWithPlaceIdAndTicketTypeDTO eventDTO;
    private TicketTypeDTO ticketTypeDTO;
}
