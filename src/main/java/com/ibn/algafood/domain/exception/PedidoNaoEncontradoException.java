package com.ibn.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PedidoNaoEncontradoException(Long id) {
        this(String.format("Não exite pedido com código %d.", id));
    }
}
