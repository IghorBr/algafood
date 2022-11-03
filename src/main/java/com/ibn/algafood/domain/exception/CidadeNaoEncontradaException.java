package com.ibn.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CidadeNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de cidade com código %d", id));
    }
}
