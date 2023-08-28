package com.ritam.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND)
public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException() {
    }

    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamNotFoundException(Throwable cause) {
        super(cause);
    }

    public TeamNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
