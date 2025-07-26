package com.arzuahmed.ticketingsystem.exception.handler;

import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventExistsException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventNotFoundException;
import com.arzuahmed.ticketingsystem.exception.eventExceptions.EventsNotFoundException;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserAlreadyExistException;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UserNotFound;
import com.arzuahmed.ticketingsystem.exception.userExceptions.UsersNotFound;
import com.arzuahmed.ticketingsystem.model.response.CommonResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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


//--------------------------------------Event Handler Exceptions----------------------------------------------------

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




}
