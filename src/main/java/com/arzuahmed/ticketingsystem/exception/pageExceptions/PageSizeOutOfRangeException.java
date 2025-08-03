package com.arzuahmed.ticketingsystem.exception.pageExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class PageSizeOutOfRangeException extends CustomException {
    public PageSizeOutOfRangeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PageSizeOutOfRangeException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
