package com.arzuahmed.ticketingsystem.exception.placeExceptions;

public class PlaceNotFoundException extends RuntimeException{
    public PlaceNotFoundException(String message){
        super(message);
    }
}
