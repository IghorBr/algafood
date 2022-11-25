package com.ibn.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProdutoNaoEncontradoException(Long produtoId, String nomeRestaurante) {
        this(String.format("Não existe cadastro de produto de código %d no restaurante %s.", produtoId, nomeRestaurante));
    }
}
