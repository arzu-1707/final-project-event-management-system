package com.arzuahmed.ticketingsystem.service;

import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import com.arzuahmed.ticketingsystem.service.impl.TicketService;

import java.util.Optional;

public interface TicketTypeServiceInterface {
    TicketType addTicketTypeInEvent(Long eventId, TicketTypeDTO ticketTypeDTO);

    Optional<TicketType> findByEventIdAndTicketTypeName(Long eventId, TICKETTYPENAME ticketTypeName);

    TicketType findTicketTypeById(Long ticketTypeId);
}
