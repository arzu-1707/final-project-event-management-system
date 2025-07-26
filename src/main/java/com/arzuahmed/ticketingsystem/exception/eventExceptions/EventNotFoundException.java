package com.arzuahmed.ticketingsystem.exception.eventExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class EventNotFoundException extends CustomException {
    public EventNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }
    public EventNotFoundException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
