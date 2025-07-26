package com.arzuahmed.ticketingsystem.exception.eventExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class EventExistsException extends CustomException {
    public EventExistsException(ErrorCode errorCode){
        super(errorCode);
    }
    public EventExistsException(ErrorCode errorCode, String message){
        super(errorCode, message);
    }
}
