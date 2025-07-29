package com.arzuahmed.ticketingsystem.exception.ValidationException;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

import java.net.PortUnreachableException;
import java.security.PublicKey;

public class TicketCountMismatchException extends CustomException {
    public TicketCountMismatchException(ErrorCode errorCode){
        super(errorCode);
    }

    public TicketCountMismatchException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
