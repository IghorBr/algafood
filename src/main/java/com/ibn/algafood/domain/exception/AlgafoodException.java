package com.ibn.algafood.domain.exception;

public class AlgafoodException extends RuntimeException {

    public AlgafoodException(String message) {
        super(message);
    }

    public AlgafoodException(String message, Throwable cause) {
        super(message, cause);
    }
}
