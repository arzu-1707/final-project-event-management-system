package com.arzuahmed.ticketingsystem.exception.userExceptions;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;

public class UsersNotFound extends CustomException {
    public UsersNotFound(ErrorCode errorCode){
        super(errorCode);
    }


    public UsersNotFound(ErrorCode errorCode,String message){

        super(errorCode,message);
    }

}
