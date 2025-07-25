package com.arzuahmed.ticketingsystem.exception.ValidationException;

public class TicketCountMismatchException extends RuntimeException{
    public TicketCountMismatchException(String message){
        super(message);
    }
}
