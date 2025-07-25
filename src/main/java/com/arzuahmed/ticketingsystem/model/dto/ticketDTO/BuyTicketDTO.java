package com.arzuahmed.ticketingsystem.model.dto.ticketDTO;

import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketDTO {
        private Long eventId;
        private Integer ticketNo;
}
