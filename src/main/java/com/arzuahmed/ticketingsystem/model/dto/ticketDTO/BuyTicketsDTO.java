package com.arzuahmed.ticketingsystem.model.dto.ticketDTO;

import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketsDTO {
    private Long eventId;
    private List<Integer> ticketNo;
}
