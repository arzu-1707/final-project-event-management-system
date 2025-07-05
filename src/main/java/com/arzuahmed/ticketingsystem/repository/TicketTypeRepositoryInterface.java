package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepositoryInterface extends JpaRepository<TicketType, Long> {
}
