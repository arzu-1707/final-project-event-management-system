package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;

import java.util.List;

public interface TicketServiceInterface {
    void saveAll(List<Ticket> tickets);
    Ticket buyTicket(Long userId, BuyTicketDTO buyTicketDTO);

    void buyTickets(Long userId, BuyTicketsDTO buyTicketsDTO);
}
