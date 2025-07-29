package com.arzuahmed.ticketingsystem.exception.placeExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class PlaceNotFoundException extends CustomException {
    public PlaceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
    public PlaceNotFoundException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
