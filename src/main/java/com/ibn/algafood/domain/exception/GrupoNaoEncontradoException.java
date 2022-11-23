package com.ibn.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de grupo com código %d", id));
    }
}
