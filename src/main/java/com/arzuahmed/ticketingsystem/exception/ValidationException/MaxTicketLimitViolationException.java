package com.arzuahmed.ticketingsystem.exception.ValidationException;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class MaxTicketLimitViolationException extends CustomException {
    public MaxTicketLimitViolationException(ErrorCode errorCode){
        super(errorCode);
    }
    public MaxTicketLimitViolationException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
