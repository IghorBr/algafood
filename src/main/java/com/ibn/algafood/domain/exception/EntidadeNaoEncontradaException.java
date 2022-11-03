package com.ibn.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends AlgafoodException {

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

    public EntidadeNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
