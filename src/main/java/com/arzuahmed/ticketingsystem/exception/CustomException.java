package com.arzuahmed.ticketingsystem.exception;

import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message, Throwable cause){
        super(message, cause);
        this.errorCode = errorCode;
    }

}
