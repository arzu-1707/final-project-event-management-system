package com.arzuahmed.ticketingsystem.exception.userExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UserNotFound extends CustomException {
    public UserNotFound(ErrorCode errorCode) {

        super(errorCode);
    }

    public UserNotFound(ErrorCode errorCode, String message){
        super(errorCode,message);
    }
}
