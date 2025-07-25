package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepositoryInterface extends JpaRepository<Ticket, Long> {


    Optional<Ticket> findByEventIdAndTicketTypeAndTicketNoAndStatus(Long eventId, TicketType ticketType, @Min(1) @Positive Integer ticketNo, STATUS status);

    boolean existsTicketByTicketNo(@Min(1) @Positive Integer ticketNo);

    boolean existsByTicketNo(@Min(1) @Positive Integer ticketNo);

    int existsByEvent_IdAndTicketNo(Long eventİd, @Min(1) @Positive Integer ticketNo);

    int existsByEventAndTicketNo(Event event, @Min(1) @Positive Integer ticketNo);

    Object existsByTicketNoAndStatus(@Min(1) @Positive Integer ticketNo, STATUS status);

    Object existsTicketByEventAndTicketNoAndStatus(Event event, @Min(1) @Positive Integer ticketNo, STATUS status);

    List<Ticket> findTicketsByEventAndTicketNoAndStatus(Event event, @Min(1) @Positive Integer ticketNo, STATUS status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)      // Eyni anda 2 neferin bilet almasinin qarsisini alir
    List<Ticket> findAllByEventAndTicketNoAndStatus(Event event, @Min(1) @Positive Integer ticketNo, STATUS status);

    List<Ticket> findByTicketNo(@Min(1) @Positive Integer ticketNo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Ticket> findTicketByEventAndTicketNoAndStatus(Event event, @Min(1) @Positive Integer ticketNo, STATUS status);
    // Ticket findTicketsById(Long id);
}
