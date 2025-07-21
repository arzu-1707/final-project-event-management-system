package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketTypeNotFound;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import com.arzuahmed.ticketingsystem.repository.TicketTypeRepository;
import com.arzuahmed.ticketingsystem.service.TicketServiceInterface;
import com.arzuahmed.ticketingsystem.service.TicketTypeServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketTypeService implements TicketTypeServiceInterface {
    private final TicketTypeRepository ticketTypeRepository;
    private final EventService eventService;


    //Event-e TicketType elave edilmesi
    @Override
    public TicketType addTicketTypeInEvent(Long eventId, TicketTypeDTO ticketTypeDTO) {
        Event event = eventService.findEventById(eventId);
        TicketType ticketType = Mapper.ticketTypeMapper(ticketTypeDTO);
        ticketType.setEvent(event);
        return ticketTypeRepository.save(ticketType);
    }

    //Event-de ticketTypeName-e gore TicketType-in oldugunu yoxlayir...
    @Override
    public Optional<TicketType> findByEventIdAndTicketTypeName(Long eventId, TICKETTYPENAME ticketTypeName) {
        return ticketTypeRepository.findByEvent_IdAndTicketTypeName(eventId, ticketTypeName);
    }

}
