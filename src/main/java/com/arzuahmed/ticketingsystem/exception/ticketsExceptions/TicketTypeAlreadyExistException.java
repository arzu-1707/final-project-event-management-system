package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class TicketTypeAlreadyExistException extends CustomException {
    public TicketTypeAlreadyExistException(ErrorCode errorCode){
        super(errorCode);
    }
    public TicketTypeAlreadyExistException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
