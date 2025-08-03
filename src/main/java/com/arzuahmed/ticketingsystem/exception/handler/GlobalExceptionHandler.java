package com.arzuahmed.ticketingsystem.exception.handler;

import com.arzuahmed.ticketingsystem.exception.CustomException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.CapacityExceededException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.MaxTicketLimitViolationException;
import com.arzuahmed.ticketingsystem.exception.ValidationException.TicketCountMismatchException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventExistsException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.pageExceptions.PageNumberOutOfRangeException;
import com.arzuahmed.ticketingsystem.exception.pageExceptions.PageSizeOutOfRangeException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceAlreadyExistsException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlaceNotFoundException;
import com.arzuahmed.ticketingsystem.exception.placeExceptions.PlacesNotFoundException;
import com.arzuahmed.ticketingsystem.exception.ticketsExceptions.*;
import com.arzuahmed.ticketingsystem.exception.userExceptions.InvalidOldPasswordException;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserAlreadyExistException;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UsersNotFound;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import com.arzuahmed.ticketingsystem.model.response.CommonResponseError;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
    public ResponseEntity<CommonResponseError> handleUsersNotFoundException(
            UsersNotFound ex, HttpServletRequest request
    )
    {
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),
                ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<CommonResponseError> handleUserAlreadyExistException(
            UserAlreadyExistException ex, HttpServletRequest request
    ){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),
                ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidOldPasswordException.class)
    public ResponseEntity<CommonResponseError> handleInvalidOldPasswordException(
            InvalidOldPasswordException ex, HttpServletRequest request
    ){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),
                ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Event Exceptions Handler----------------------------------------------------

@ExceptionHandler(EventNotFoundException.class)
public ResponseEntity<CommonResponseError> handlerEventNotFoundException(
        EventNotFoundException ex, HttpServletRequest request
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
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
}


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Place Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(PlaceAlreadyExistsException.class)
    public ResponseEntity<CommonResponseError> handlePlaceAlreadyExistsException(PlaceAlreadyExistsException ex,
                                                                                 HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<CommonResponseError> handlePlaceNotFoundException(PlaceNotFoundException ex,
                                                                            HttpServletRequest request){
        CommonResponseError error =CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlacesNotFoundException.class)
    public ResponseEntity<CommonResponseError> handlePlacesNotFoundException(PlacesNotFoundException ex,
                                                                             HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new  ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Ticket Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(TicketAlreadySoldException.class)
    public ResponseEntity<CommonResponseError> handleTicketAlreadySoldException(TicketAlreadySoldException ex,
                                                                                HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketNotAvailable.class)
    public ResponseEntity<CommonResponseError> handleTicketNotAvailableException(TicketNotAvailable ex,
                                                                                 HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<CommonResponseError> handleTicketNotFoundException(TicketNotFoundException ex,
                                                                        HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketsNotFoundException.class)
    public ResponseEntity<CommonResponseError> handleTicketsNotFoundException(TicketsNotFoundException ex,
                                                                              HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Ticket Type Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(TicketTypeAlreadyExistException.class)
    public ResponseEntity<CommonResponseError> handleTicketTypeAlreadyExistException(TicketTypeAlreadyExistException ex,
                                                                                     HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketTypeNotFound.class)
    public ResponseEntity<CommonResponseError> handleTicketTypeNotFoundException(TicketTypeNotFound ex,
                                                                                 HttpServletRequest request){
        CommonResponseError error =CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


//------------------------------------------------------------------------------------------------------------------


//--------------------------------------Validate Exceptions Handler-----------------------------------------------------

    @ExceptionHandler(CapacityExceededException.class)
    public ResponseEntity<CommonResponseError> handleCapacityExceededException(CapacityExceededException ex,
                                                                               HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxTicketLimitViolationException.class)
    public ResponseEntity<CommonResponseError> handleMaxTicketsLimitViolationException(MaxTicketLimitViolationException ex,
                                                                                       HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketCountMismatchException.class)
    public ResponseEntity<CommonResponseError> handleTicketCountMismatchException(TicketCountMismatchException ex,
                                                                                  HttpServletRequest request){
        CommonResponseError error = CommonResponseError.of(ex.getErrorCode().getCode(),ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }



//----------------------------------------------------Common exceptions------------------------------------------------


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponseError> handleCustomException(
            CustomException ex, HttpServletRequest request) {
        CommonResponseError errorResponse = CommonResponseError.of(
                ex.getErrorCode().getCode(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponseError> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {

        CommonResponseError errorResponse = CommonResponseError.of(
                ErrorCode.ACCESS_DENIED.getCode(),
                "Sizin bu əməliyyatı yerinə yetirməyə icazəniz yoxdur",
                request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<CommonResponseError> handleAuthorizationDeniedException(
            AuthorizationDeniedException ex, HttpServletRequest request) {

        CommonResponseError errorResponse = CommonResponseError.of(
                ErrorCode.ACCESS_DENIED.getCode(),
                "Sizin bu əməliyyatı yerinə yetirməyə icazəniz yoxdur",
                request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN); // 403
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseError> handleGlobalException(
            Exception ex, HttpServletRequest request) {
        GlobalExceptionHandler.log.error("Unhandled exception occurred", ex);
        CommonResponseError errorResponse = CommonResponseError.of(
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CommonResponseError> handleNullPointerException(
            NullPointerException ex, HttpServletRequest request
    ){
        CommonResponseError commonResponseError = CommonResponseError.of(
                ErrorCode.NULL_POINTER_EXCEPTION.getCode(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(commonResponseError, HttpStatus.NOT_FOUND);
    }



//--------------------------------------------Page Exceptions-----------------------------------------------------

    @ExceptionHandler(PageNumberOutOfRangeException.class)
    public ResponseEntity<CommonResponseError> handlePageNumberOutOfRangeException(
            PageNumberOutOfRangeException ex, HttpServletRequest request
    ){
        CommonResponseError commonResponseError = CommonResponseError.of(
                ErrorCode.PAGE_SIZE_OUT_OF_RANGE_EXCEPTION.getCode(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(commonResponseError, HttpStatus.CONFLICT);

    }


    @ExceptionHandler(PageSizeOutOfRangeException.class)
    public ResponseEntity<CommonResponseError> handlePageSizeOutOfRangeException(
            PageSizeOutOfRangeException ex, HttpServletRequest request
    ){
        CommonResponseError error = CommonResponseError.of(
                ErrorCode.PAGE_SIZE_OUT_OF_RANGE_EXCEPTION.getCode(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
