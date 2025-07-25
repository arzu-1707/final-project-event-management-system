package com.arzuahmed.ticketingsystem.exception.ValidationException;

public class MaxTicketLimitViolationException extends RuntimeException{
    public MaxTicketLimitViolationException(String message){
        super(message);
    }
}
