package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //404
public class TicketNotFoundException extends CustomException {
    public TicketNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }
    public TicketNotFoundException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
