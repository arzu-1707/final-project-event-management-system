package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

public class TicketTypeNotFound extends RuntimeException{
    public  TicketTypeNotFound(String message){
        super(message);
    };
}
