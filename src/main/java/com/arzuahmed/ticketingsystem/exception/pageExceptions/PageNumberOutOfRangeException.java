package com.arzuahmed.ticketingsystem.exception.pageExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class PageNumberOutOfRangeException extends CustomException {
    public PageNumberOutOfRangeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PageNumberOutOfRangeException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
