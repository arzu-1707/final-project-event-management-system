package com.arzuahmed.ticketingsystem.exception.eventExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class EventsNotFoundException extends CustomException {

    public EventsNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }

    public EventsNotFoundException(ErrorCode errorCode,String message){

        super(errorCode,message);
    }
}
