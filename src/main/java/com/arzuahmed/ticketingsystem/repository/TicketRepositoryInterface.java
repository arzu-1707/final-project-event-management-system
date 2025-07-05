package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepositoryInterface extends JpaRepository<Ticket, Long> {
}
