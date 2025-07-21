package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepositoryInterface extends JpaRepository<Ticket, Long> {


    Optional<Ticket> findByEventIdAndTicketTypeAndTicketNoAndStatus(Long eventId, TicketType ticketType, @Min(1) @Positive Integer ticketNo, STATUS status);
    // Ticket findTicketsById(Long id);
}
