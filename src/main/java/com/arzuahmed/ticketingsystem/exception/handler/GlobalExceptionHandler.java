package com.arzuahmed.ticketingsystem.exception.handler;

import com.arzuahmed.ticketingsystem.exception.ValidationException.CapacityExceededException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.MaxTicketLimitViolationException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.TicketCountMismatchException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventExistsException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceAlreadyExistsException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlacesNotFoundException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.*;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserAlreadyExistException;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UsersNotFound;
import com.arzuahmed.ticketingsystem.model.response.CommonResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.PortUnreachableException;

@RestControllerAdvice
public class GlobalExceptionHandler {


//-----------------------------------User Exceptions handler------------------------------------------------
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<CommonResponseError> handlerUserNotFoundException(
            UserNotFound ex, HttpServletRequest request)
    {
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),
                ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UsersNotFound.class)
    public ResponseEntity<CommonResponseError> handlerUsersNotFoundException(
            UserNotFound ex, HttpServletRequest request
    )
    {
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),
                ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<CommonResponseError> handlerUserAlreadyExistException(
            UserAlreadyExistException ex, HttpServletRequest request
    ){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),
                ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Event Exceptions Handler----------------------------------------------------

@ExceptionHandler(EventNotFoundException.class)
public ResponseEntity<CommonResponseError> handlerEventNotFoundException(
        EventExistsException ex, HttpServletRequest request
)    {
    CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),
            ex.getMessage(), request.getRequestURI());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}


@ExceptionHandler(EventsNotFoundException.class)
    public ResponseEntity<CommonResponseError> handlerEventsNotFoundException(
            EventsNotFoundException ex, HttpServletRequest request
){
    CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), ex.getMessage(), request.getRequestURI());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(EventExistsException.class)
    public ResponseEntity<CommonResponseError> handlerEventExistsException(EventExistsException ex,
                                                                           HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
}


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Place Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(PlaceAlreadyExistsException.class)
    public ResponseEntity<CommonResponseError> handlePlaceAlreadyExistsException(PlaceAlreadyExistsException ex,
                                                                                 HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<CommonResponseError> handlePlaceNotFoundException(PlaceNotFoundException ex,
                                                                            HttpServletRequest request){
        CommonResponseError error =CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlacesNotFoundException.class)
    public ResponseEntity<CommonResponseError> handlePlacesNotFoundException(PlacesNotFoundException ex,
                                                                             HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new  ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Ticket Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(TicketAlreadySoldException.class)
    public ResponseEntity<CommonResponseError> handleTicketAlreadySoldException(TicketAlreadySoldException ex,
                                                                                HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketNotAvailable.class)
    public ResponseEntity<CommonResponseError> handleTicketNotAvailableException(TicketNotAvailable ex,
                                                                                 HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<CommonResponseError> handleTicketNotFoundException(TicketNotFoundException ex,
                                                                        HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketsNotFoundException.class)
    public ResponseEntity<CommonResponseError> handleTicketsNotFoundException(TicketsNotFoundException ex,
                                                                              HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Ticket Type Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(TicketTypeAlreadyExistException.class)
    public ResponseEntity<CommonResponseError> handleTicketTypeAlreadyExistException(TicketTypeAlreadyExistException ex,
                                                                                     HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketTypeNotFound.class)
    public ResponseEntity<CommonResponseError> handleTicketTypeNotFoundException(TicketTypeNotFound ex,
                                                                                 HttpServletRequest request){
        CommonResponseError error =CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Validate Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(CapacityExceededException.class)
    public ResponseEntity<CommonResponseError> handleCapacityExceededException(CapacityExceededException ex,
                                                                               HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxTicketLimitViolationException.class)
    public ResponseEntity<CommonResponseError> handleMaxTicketsLimitViolationException(MaxTicketLimitViolationException ex,
                                                                                       HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketCountMismatchException.class)
    public ResponseEntity<CommonResponseError> handleTicketCountMismatchException(TicketCountMismatchException ex,
                                                                                  HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }



}
