package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends AlgafoodException {

    public EntidadeEmUsoException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public EntidadeEmUsoException(String reason) {
        this(HttpStatus.CONFLICT, reason);
    }
}
