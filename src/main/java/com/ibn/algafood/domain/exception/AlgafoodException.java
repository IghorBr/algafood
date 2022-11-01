package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlgafoodException extends RuntimeException {

    public AlgafoodException(String message) {
        super(message);
    }

    public AlgafoodException(String message, Throwable cause) {
        super(message, cause);
    }
}
