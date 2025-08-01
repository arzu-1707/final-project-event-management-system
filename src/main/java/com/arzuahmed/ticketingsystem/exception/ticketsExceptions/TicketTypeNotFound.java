package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class TicketTypeNotFound extends CustomException {
    public TicketTypeNotFound(ErrorCode errorCode){
        super(errorCode);
    }
    public  TicketTypeNotFound(ErrorCode errorCode, String message){
        super(errorCode,message);
    };
}
