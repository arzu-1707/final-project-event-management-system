package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    Optional<TicketType> findByEvent_IdAndTicketTypeName(Long eventId, TICKETTYPENAME ticketTypeName);
}
