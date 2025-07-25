package com.arzuahmed.ticketingsystem.exception.ValidationException;

public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException(String message){
        super(message);
    }
}
