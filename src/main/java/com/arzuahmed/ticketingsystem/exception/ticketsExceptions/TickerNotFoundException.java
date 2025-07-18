package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //404
public class TickerNotFoundException extends RuntimeException{
    public TickerNotFoundException(String message){
        super(message);
    }
}
