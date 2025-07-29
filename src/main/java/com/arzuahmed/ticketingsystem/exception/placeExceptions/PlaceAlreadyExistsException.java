package com.arzuahmed.ticketingsystem.exception.placeExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class PlaceAlreadyExistsException extends CustomException {
    public PlaceAlreadyExistsException(ErrorCode errorCode){
        super(errorCode);
    }
    public PlaceAlreadyExistsException(ErrorCode errorCode, String message){
        super(errorCode, message);
    }
}
