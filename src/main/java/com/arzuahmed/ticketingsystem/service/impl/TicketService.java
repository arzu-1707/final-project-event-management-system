package com.arzuahmed.ticketingsystem.service.impl;

import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketAlreadySoldException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.TicketNotAvailable;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.BuyTicketsDTO;
import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.arzuahmed.ticketingsystem.repository.TicketRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.TicketServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public void saveAll(List<Ticket> tickets) {
        ticketRepository.saveAll(tickets);
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

//    @Override
//    @Transactional
//    public void buyTickets(Long userId, BuyTicketsDTO buyTicketsDTO) {
//        User user = userService.findUserById(userId);
//        Event event = eventService.findEventById(buyTicketsDTO.getEventId());
//        List<Integer> ticketNo = buyTicketsDTO.getTicketNo();
//        List<Ticket> tickets = new ArrayList<>();
//        for (Integer ticket : ticketNo){
//            List<Ticket> foundTickets = ticketRepository.findAllByEventAndTicketNoAndStatus(event, ticket, STATUS.AVAILABLE);
//              tickets.addAll(foundTickets);
//        }
//
//        if (ticketNo.size()!= tickets.size()){
//            throw new TicketAlreadySoldException("Ticket Already sold");
//        }
//
//        for (Ticket ticket : tickets) {
//            ticket.setPurchaseDate(LocalDateTime.now());
//            user.addTicket(ticket);
//            ticket.setStatus(STATUS.SOLD);
//        }
//
//        String ticketNumber = ticketNo.stream()
//                .map(Object::toString)
//                .collect(Collectors.joining(", "));
//
//        String body = """
//                Salam %s
//
//                Siz biletlerinizi uğurla aldınız!
//
//                Tedbir  :  %s
//                Biletin nomresi :  %s
//                Tedbirin kecirileceyi yer: %s
//                Tarix:   %s
//
//
//                Hormetle Ticketing System!""".formatted(user.getUserName(), event.getName(), ticketNumber,
//                event.getPlace().getPlaceName(),
//                event.getEventDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy,   HH:mm")));
//
//        emailService.sendEmail(user.getEmail(), "Biletleriniz Ugurla alinmisdir!!!", body);
//
//        ticketRepository.saveAll(tickets);
//        userService.save(user);
//    }


}
