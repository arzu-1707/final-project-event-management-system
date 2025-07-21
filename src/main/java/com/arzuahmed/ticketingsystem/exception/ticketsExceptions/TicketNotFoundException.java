package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //404
public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(String message){
        super(message);
    }
}
