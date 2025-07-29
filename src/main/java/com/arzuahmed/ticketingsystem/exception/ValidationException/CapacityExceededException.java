package com.arzuahmed.ticketingsystem.exception.ValidationException;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class CapacityExceededException extends CustomException {
    public CapacityExceededException(ErrorCode errorCode){
        super(errorCode);
    }
    public CapacityExceededException(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
