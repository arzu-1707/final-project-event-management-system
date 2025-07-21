package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

public class TicketNotAvailable extends RuntimeException{
    public TicketNotAvailable(String message){
        super(message);
    }
}
