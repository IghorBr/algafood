package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlgafoodException extends ResponseStatusException {

    public AlgafoodException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public AlgafoodException(String reason) {
        this(HttpStatus.BAD_REQUEST, reason);
    }
}
