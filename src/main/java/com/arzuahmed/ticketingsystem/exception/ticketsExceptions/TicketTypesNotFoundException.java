package com.arzuahmed.ticketingsystem.exception.ticketsExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class TicketTypesNotFoundException extends CustomException {
    public TicketTypesNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TicketTypesNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
