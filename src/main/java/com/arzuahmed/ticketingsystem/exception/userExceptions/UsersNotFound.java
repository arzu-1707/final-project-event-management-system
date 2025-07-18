package com.arzuahmed.ticketingsystem.exception.userExceptions;

public class UsersNotFound extends RuntimeException{
    public UsersNotFound(String message){
        super(message);
    }
}
