package com.arzuahmed.ticketingsystem.exception.placeExceptions;

public class PlaceAlreadyExistsException extends RuntimeException{
    public PlaceAlreadyExistsException(String message){
        super(message);
    }
}
