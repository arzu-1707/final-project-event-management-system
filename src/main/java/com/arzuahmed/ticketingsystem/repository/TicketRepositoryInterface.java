package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepositoryInterface extends JpaRepository<Ticket, Long> {
    Ticket findTicketsById(Long id);
}
