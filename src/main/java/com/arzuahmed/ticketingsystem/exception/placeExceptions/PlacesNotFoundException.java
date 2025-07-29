package com.arzuahmed.ticketingsystem.exception.placeExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class PlacesNotFoundException extends CustomException {
    public PlacesNotFoundException( ErrorCode errorCode){
        super(errorCode);
    }

    public PlacesNotFoundException(ErrorCode errorCode,String message){
        super(errorCode,message);
    }
}
