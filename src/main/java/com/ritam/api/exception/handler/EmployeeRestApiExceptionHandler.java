package com.ritam.api.exception.handler;

import com.ritam.api.exception.*;
import com.ritam.api.exception.error.EmployeeErrorResponse;
import com.ritam.api.exception.error.TeamErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class EmployeeRestApiExceptionHandler {

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<EmployeeErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e,
                                                                                 HttpServletRequest request){
        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TeamNotFoundException.class)
    public ResponseEntity<TeamErrorResponse> handleTeamNotFoundException(TeamNotFoundException e,
                                                                             HttpServletRequest request){
        TeamErrorResponse errorResponse = new TeamErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UsernameAlreadyExistsException.class)
    public ResponseEntity<EmployeeErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e,
                                                                                 HttpServletRequest request){
        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponse.setHttpStatus(HttpStatus.CONFLICT.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = DuplicateTeamException.class)
    public ResponseEntity<EmployeeErrorResponse> handleDuplicateTeamException(DuplicateTeamException e,
                                                                                      HttpServletRequest request){
        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponse.setHttpStatus(HttpStatus.CONFLICT.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = PayloadValidationFailedException.class)
    public ResponseEntity<EmployeeErrorResponse> handlePayloadValidationFailedException(PayloadValidationFailedException e,
                                                                                        HttpServletRequest request){
        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
