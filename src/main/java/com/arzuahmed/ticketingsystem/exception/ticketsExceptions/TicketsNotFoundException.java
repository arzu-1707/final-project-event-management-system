package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class TicketsNotFoundException extends CustomException {
    public TicketsNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }

    public TicketsNotFoundException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
