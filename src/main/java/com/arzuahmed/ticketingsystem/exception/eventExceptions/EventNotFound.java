package com.arzuahmed.ticketingsystem.exception.eventExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //404
public class EventNotFound extends RuntimeException{
    public EventNotFound(String message){
        super(message);
    }
}
