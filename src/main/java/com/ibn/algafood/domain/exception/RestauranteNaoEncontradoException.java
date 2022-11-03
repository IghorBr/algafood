package com.ibn.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de restaurante com código %d", id));
    }
}
