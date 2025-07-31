package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketTypeResponseDTO;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepositoryInterface extends JpaRepository<Ticket, Long> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)      // Eyni anda 2 neferin bilet almasinin qarsisini alir
    List<Ticket> findAllByEventAndTicketNoAndStatus(Event event, @Min(1) @Positive Integer ticketNo, STATUS status);


    @Lock(LockModeType.PESSIMISTIC_WRITE)

    Optional<List<Ticket>> findTicketsByStatus(STATUS status);


}
