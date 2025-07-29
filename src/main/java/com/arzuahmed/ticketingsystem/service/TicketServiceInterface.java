package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketServiceInterface {
    void saveAll(List<Ticket> tickets);

    List<TicketResponseDTO> findAllTickets(Long userId);
    // void buyTicket(Long userId, BuyTicketDTO buyTicketDTO);

    List<TicketResponseDTO> buyTickets(Long userId, BuyTicketsDTO buyTicketsDTO);

    List<TicketResponseDTO> findAllTicketsFromEvent(Long eventId);
}
