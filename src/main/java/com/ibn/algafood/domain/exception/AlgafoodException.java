package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlgafoodException extends ResponseStatusException {

    public AlgafoodException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
