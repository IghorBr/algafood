package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends AlgafoodException {

    public EntidadeEmUsoException(String message) {
        super(message);
    }

    public EntidadeEmUsoException(String message, Throwable cause) {
        super(message, cause);
    }
}
