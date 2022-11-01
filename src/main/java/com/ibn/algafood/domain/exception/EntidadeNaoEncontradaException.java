package com.ibn.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaException extends AlgafoodException {

    public EntidadeNaoEncontradaException(String reason) {
        this(HttpStatus.NOT_FOUND, reason);
    }

    public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
