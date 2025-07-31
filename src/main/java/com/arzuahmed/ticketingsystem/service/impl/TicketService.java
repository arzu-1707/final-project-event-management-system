package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketAlreadySoldException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketNotAvailable;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketsNotFoundException;
import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketsDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.TicketType;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketTypeResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketsResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventAndAvailableTicketResponse;
import com.arzuahmed.ticketingsystem.repository.TicketRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.TicketServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService implements TicketServiceInterface {

    private final TicketRepositoryInterface ticketRepository;
    private final EventService eventService;
    private final UserService userService;
    private final TicketTypeService ticketTypeService;
    private final EmailService emailService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void saveAll(List<Ticket> tickets) {
        ticketRepository.saveAll(tickets);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<TicketResponseDTO> findAllTickets(Long userId) {
        User user = userService.findById(userId);
        List<Ticket> tickets = user.getTickets();
       return Mapper.ticketResponseListMapper(tickets);
    }


//    @Override
//    @Transactional
//    public void buyTicket(Long userId, BuyTicketDTO buyTicketDTO) {
//        User user = userService.findUserById(userId);
//        Event event = eventService.findEventById(buyTicketDTO.getEventId());
//        Integer ticketNo = buyTicketDTO.getTicketNo();
//        Ticket ticket = ticketRepository.findTicketByEventAndTicketNoAndStatus(event,
//                ticketNo, STATUS.AVAILABLE).orElseThrow(() -> new TicketNotAvailable("Ticket Not Available"));
//        ticket.setStatus(STATUS.SOLD);
//        ticket.setUser(user);
//        ticket.setPurchaseDate(LocalDateTime.now());
//        String body = """
//                Salam %s
//
//                Siz biletinizi ugurla aldiniz!!
//
//                Tedbir  :  %s
//                Biletin nomresi :  %d
//                Tedbirin kecirileceyi yer: %s
//                Tarix:   %s
//
//
//                Hormetle Ticketing System!
//                """.formatted(user.getUserName(), event.getName(),
//                ticket.getTicketNo(), event.getPlace().getPlaceName(),
//                event.getEventDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy,   HH:mm")));
//
//        emailService.sendEmail(user.getEmail(), "Bilet Ugurla alinmisdir..", body );
//        user.addTicket(ticket);
//        ticketRepository.save(ticket);
//        userService.save(user);
//
//    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<TicketResponseDTO> buyTickets(Long userId, BuyTicketsDTO buyTicketsDTO) {
        User user = userService.findById(userId);
        Event event = eventService.findEventById(buyTicketsDTO.getEventId());
        List<Integer> ticketNo = buyTicketsDTO.getTicketNo();
        List<Ticket> tickets = new ArrayList<>();
        for (Integer ticket : ticketNo){
            List<Ticket> foundTickets = ticketRepository
                    .findAllByEventAndTicketNoAndStatus(event, ticket, STATUS.AVAILABLE);
              tickets.addAll(foundTickets);
        }

        if (ticketNo.size()!= tickets.size()){
            throw new TicketAlreadySoldException(ErrorCode.TICKET_ALREADY_SOLD);
        }

        for (Ticket ticket : tickets) {
            ticket.setPurchaseDate(LocalDateTime.now());
            user.addTicket(ticket);
            ticket.setStatus(STATUS.SOLD);
        }
        int count = event.getAvailableTickets() - tickets.size();
        event.setAvailableTickets(count);

        String ticketNumber = ticketNo.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String body = """
                Salam %s

                Siz biletlerinizi uğurla aldınız!

                Tedbir  :  %s
                Biletin nomresi :  %s
                Tedbirin kecirileceyi yer: %s
                Tarix:   %s


                Hormetle Ticketing System!""".formatted(user.getUserName(),
                event.getName(), ticketNumber,
                event.getPlace().getPlaceName(),
                event.getEventDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy,   HH:mm")));

        emailService.sendEmail(user.getEmail(), "Biletleriniz Ugurla alinmisdir!!!", body);

        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);
        userService.save(user);
       return Mapper.ticketResponseListMapper(savedTickets);
    }

    @Override
    public List<TicketResponseDTO> findAllTicketsFromEvent(Long eventId) {
        Event event = eventService.findEventById(eventId);
        List<Ticket> tickets = event.getTickets();
        if (tickets.isEmpty()){
            throw new TicketsNotFoundException(ErrorCode.TICKETS_NOT_FOUND);
        }
        return Mapper.ticketResponseListMapper(tickets);
    }




}
