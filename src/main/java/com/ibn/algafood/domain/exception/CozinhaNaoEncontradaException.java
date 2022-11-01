package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de cozinha com código %d", id));
    }
}
