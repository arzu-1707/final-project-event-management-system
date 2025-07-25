package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

public class TicketTypeAlreadyExistException extends RuntimeException{
    public TicketTypeAlreadyExistException(String message){
        super(message);
    }
}
