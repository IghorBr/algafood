package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String reason) {
        this(HttpStatus.NOT_FOUND, reason);
    }

    public CozinhaNaoEncontradaException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de cozinha com código %d", id));
    }
}
