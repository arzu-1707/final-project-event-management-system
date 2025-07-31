package com.arzuahmed.ticketingsystem.exception.userExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class InvalidOldPasswordException extends CustomException {

    public InvalidOldPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidOldPasswordException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
