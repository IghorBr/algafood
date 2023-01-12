package com.ibn.algafood.domain.exception;

public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public FotoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public FotoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
                produtoId, restauranteId));
    }
}
