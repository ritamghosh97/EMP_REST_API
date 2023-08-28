package com.ritam.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, value = HttpStatus.CONFLICT)
public class DuplicateTeamException extends  RuntimeException{
    public DuplicateTeamException() {
    }

    public DuplicateTeamException(String message) {
        super(message);
    }

    public DuplicateTeamException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTeamException(Throwable cause) {
        super(cause);
    }

    public DuplicateTeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
