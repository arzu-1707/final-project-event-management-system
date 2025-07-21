package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketAlreadySoldException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketNotAvailable;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketTypeNotFound;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import com.arzuahmed.ticketingsystem.repository.TicketRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.TicketServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements TicketServiceInterface {

    private final TicketRepositoryInterface ticketRepository;
    private final EventService eventService;
    private final UserService userService;
    private final TicketTypeService ticketTypeService;

    @Override
    public void saveAll(List<Ticket> tickets) {
        ticketRepository.saveAll(tickets);
    }



    @Override
    @Transactional
    public Ticket buyTicket(Long userId, BuyTicketDTO buyTicketDTO) {
        Long eventId = buyTicketDTO.getEventId();
        User user = userService.findUserById(userId);
        Integer ticketNo = buyTicketDTO.getTicketNo();


        TICKETTYPENAME ticketTypeName = buyTicketDTO.getTickettypename();

        //Eventde TicketTypeName-in olub olmamasini yoxlayir...
        TicketType ticketType = ticketTypeService.findByEventIdAndTicketTypeName(eventId, ticketTypeName)
                .orElseThrow(() -> new TicketTypeNotFound("Ticket Type Not found"));


        Ticket ticket = ticketRepository.findByEventIdAndTicketTypeAndTicketNoAndStatus(eventId,
                        ticketType, ticketNo, STATUS.AVAILABLE)
                .orElseThrow(() -> new TicketNotAvailable("Ticket not Available"));

    ticket.setUser(user);
    user.addTicket(ticket);
    ticket.setPurchaseDate(LocalDateTime.now());
    ticket.setStatus(STATUS.SOLD);
        try {
                return ticketRepository.save(ticket);
            } catch (DataIntegrityViolationException e) {
                throw new TicketAlreadySoldException("Ticket already sold");
            }
    }





}
