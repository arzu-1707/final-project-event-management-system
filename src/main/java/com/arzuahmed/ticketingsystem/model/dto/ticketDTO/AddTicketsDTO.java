package com.arzuahmed.ticketingsystem.model.dto.ticketDTO;

import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTicketsDTO {
   private List<TicketCreateDTO> tickets ;
}
