package com.arzuahmed.ticketingsystem.exception.placeExceptions;

public class PlacesNotFoundException extends RuntimeException{
    public PlacesNotFoundException(String message){
        super(message);
    }
}
