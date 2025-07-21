package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class TicketAlreadySoldException extends DataIntegrityViolationException {
    public TicketAlreadySoldException(String message){
        super(message);
    }
}
