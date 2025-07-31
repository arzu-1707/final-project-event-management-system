package com.arzuahmed.ticketingsystem.model.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //General Errors
    INTERNAL_SERVER_ERROR(1000, "Internal server error"),
    VALIDATION_ERROR(1001, "Validation error"),
    RESOURCE_NOT_FOUND(1002, "Resource not found"),
    NULL_POINTER_EXCEPTION(1003, "Null Pointer Error"),

    //Users Errors
    USER_NOT_FOUND(2000, "User not found"),
    USERS_NOT_FOUND(2001, "Users not found"),
    USER_ALREADY_EXITS_EXCEPTION(2002, "User already exists"),
    USER_INVALID_PASSWORD(2003, "Invalid password"),



    //Event Errors
    EVENT_NOT_FOUND(3000, "Event not found"),
    EVENTS_NOT_FOUND(3001, "Events not found"),
    EVENT_ALREADY_EXITS(3002, "Event already exits"),


    //Ticket Errors
    TICKET_NOT_FOUND(4000, "Ticket not found"),
    TICKETS_NOT_FOUND(4001, "Tickets not found"),
    TICKET_ALREADY_SOLD(4002, "Ticket Already sold"),
    TICKET_NOT_AVAILABLE(4003, "Ticket not available"),


    //Ticket Type Errors
    TICKET_TYPE_NOT_FOUND(4001, "Ticket Type not found"),
    TICKET_TYPE_ALREADY_EXISTS(4003, "ticket already exits"),





    //Place Errors
    PLACE_NOT_FOUND(5000, "Place not found"),
    PLACES_NOT_FOUND(5001, "Places not found"),
    PLACE_ALREADY_EXISTS_EXCEPTION(5002, "Place already exits"),


    //Validation Exceptions
    TICKET_COUNT_MISMATCH_EXCEPTION(6000, "The number of tickets is" +
            " not equal to the maximum number of tickets!"),
    CAPACITY_EXCEEDED_EXCEPTION(6001,"You have exceeded the number of tickets!!!"),
    MAX_TICKET_LIMIT_VIOLATION_EXCEPTION(6002, "You have exceeded the maximum number of tickets!!!");



    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
