package com.ritam.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, value = HttpStatus.BAD_REQUEST)
public class PayloadValidationFailedException extends RuntimeException{

    public PayloadValidationFailedException() {
    }

    public PayloadValidationFailedException(String message) {
        super(message);
    }

    public PayloadValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayloadValidationFailedException(Throwable cause) {
        super(cause);
    }

    public PayloadValidationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
