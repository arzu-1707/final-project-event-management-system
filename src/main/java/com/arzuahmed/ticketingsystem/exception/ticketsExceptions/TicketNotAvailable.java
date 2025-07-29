package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class TicketNotAvailable extends CustomException {
    public TicketNotAvailable(ErrorCode errorCode){
        super(errorCode);
    }
    public TicketNotAvailable(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
