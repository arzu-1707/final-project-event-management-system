package com.arzuahmed.ticketingsystem.exception.eventExceptions;

public class EventsNotFoundException extends RuntimeException{
    public EventsNotFoundException(String message){
        super(message);
    }
}
