package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String reason) {
        this(HttpStatus.NOT_FOUND, reason);
    }

    public RestauranteNaoEncontradoException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de restaurante com código %d", id));
    }
}
