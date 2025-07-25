package com.arzuahmed.ticketingsystem.validate;

import com.arzuahmed.ticketingsystem.exception.ValidationException.CapacityExceededException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.MaxTicketLimitViolationException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.TicketCountMismatchException;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketCreateDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;

import java.util.List;

public class Validate {

    public static void validateMaxTickets(int maxTickets, int seatCapacity){
        if (maxTickets > seatCapacity) {
            throw new CapacityExceededException("Ticketlerin sayini asdiniz!!!");
        }
    }


    public static void validateTicketCount(int ticketCount, int maxTicket){
        if (ticketCount!=maxTicket){
            throw new TicketCountMismatchException("Ticketlerin sayi Max ticketlerin sayina beraber deyil!!!");
        }
    }

    public static void validateTicketCountsFromTypeDTO(List<TicketTypeDTO> ticketCount, int maxTicket){
        int sum = ticketCount.stream()
                .mapToInt(TicketTypeDTO::getTicketCount)
                .sum();
        if (sum>maxTicket){
            throw new MaxTicketLimitViolationException("Max ticketlerin sayini asdiniz!!!");
        }
    }


    public static void validateTicketCountsFromCreateDTO(List<TicketCreateDTO> ticketCount,  int maxTicketCount){
        int sum = ticketCount.stream()
                .mapToInt(TicketCreateDTO::getTicketCount)
                .sum();

        if (sum>maxTicketCount){
            throw new MaxTicketLimitViolationException("Max ticketlerin sayini asdiniz!!!");
        }
    }


}
