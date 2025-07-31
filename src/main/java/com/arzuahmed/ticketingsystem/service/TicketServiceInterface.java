package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketsDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;

import java.util.List;

public interface TicketServiceInterface {
    void saveAll(List<Ticket> tickets);

    List<TicketResponseDTO> findAllTickets(Long userId);

    List<TicketResponseDTO> buyTickets(Long userId, BuyTicketsDTO buyTicketsDTO);

    List<TicketResponseDTO> findAllTicketsFromEvent(Long eventId);


}
