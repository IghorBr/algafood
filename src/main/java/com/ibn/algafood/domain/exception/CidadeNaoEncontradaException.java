package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(String reason) {
        this(HttpStatus.NOT_FOUND, reason);
    }

    public CidadeNaoEncontradaException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public CidadeNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de cidade com código %d", id));
    }
}
