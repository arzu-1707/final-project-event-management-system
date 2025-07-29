package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;

public class TicketAlreadySoldException extends CustomException {
    public TicketAlreadySoldException(ErrorCode errorCode){
        super(errorCode);
    }
    public TicketAlreadySoldException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
