package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public EstadoNaoEncontradoException(String reason) {
        this(HttpStatus.NOT_FOUND, reason);
    }

    public EstadoNaoEncontradoException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public EstadoNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de estado com código %d", id));
    }
}
