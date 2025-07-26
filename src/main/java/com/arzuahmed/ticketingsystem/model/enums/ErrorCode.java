package com.arzuahmed.ticketingsystem.model.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //General Errors
    INTERNAL_SERVER_ERROR(1000, "Internal server error"),
    VALIDATION_ERROR(1001, "Validation error"),
    RESOURCE_NOT_FOUND(1002, "Resource not found"),

    //Users Errors
    USER_NOT_FOUND(2000, "User not found"),
    USERS_NOT_FOUND(2001, "Users not found"),
    USER_ALREADY_EXITS_EXCEPTION(2002, "User already exists"),



    //Event Errors
    EVENT_NOT_FOUND(3000, "Event not found"),
    EVENTS_NOT_FOUND(3001, "Events not found"),
    EVENT_ALREADY_EXITS_EXCEPTION(3002, "Event already exits"),


    //Ticket Errors
    TICKET_NOT_FOUND(4000, "Ticket not found"),



    //Place Errors
    PLACE_NOT_FOUND(5000, "Place not found");



    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
