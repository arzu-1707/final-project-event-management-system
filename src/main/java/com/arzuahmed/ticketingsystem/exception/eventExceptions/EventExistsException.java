package com.arzuahmed.ticketingsystem.exception.eventExceptions;

public class EventExistsException extends RuntimeException {
    public EventExistsException(String message){
        super(message);
    }
}
