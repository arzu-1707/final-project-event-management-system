package com.arzuahmed.ticketingsystem.validate;

import com.arzuahmed.ticketingsystem.exception.ValidationException.CapacityExceededException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.MaxTicketLimitViolationException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.TicketCountMismatchException;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketCreateDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

import java.util.List;

public class Validate {

    public static void validateMaxTicketAndSeatCapacity(int maxTickets, int seatCapacity){
        if (maxTickets > seatCapacity) {
            throw new CapacityExceededException(ErrorCode.CAPACITY_EXCEEDED_EXCEPTION);
        }
    }


    public static void validateTicketCountAndMaxTicket(int ticketCount, int maxTicket){
        if (ticketCount!=maxTicket){
            throw new TicketCountMismatchException(ErrorCode.TICKET_COUNT_MISMATCH_EXCEPTION);
        }
    }

    public static void validateTicketCountsFromTypeDTOAndMaxTicket(List<TicketTypeDTO> ticketType, int maxTicket){
        int sum = ticketType.stream()
                .mapToInt(TicketTypeDTO::getTicketCount)
                .sum();
        if (sum>maxTicket){
            throw new MaxTicketLimitViolationException(ErrorCode.MAX_TICKET_LIMIT_VIOLATION_EXCEPTION);
        }
    }


    public static void validateTicketCountsFromCreateDTO(List<TicketCreateDTO> ticketCount,  int maxTicketCount){
        int sum = ticketCount.stream()
                .mapToInt(TicketCreateDTO::getTicketCount)
                .sum();

        if (sum>maxTicketCount){
            throw new MaxTicketLimitViolationException(ErrorCode.MAX_TICKET_LIMIT_VIOLATION_EXCEPTION);
        }
    }


}
