package com.ibn.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de forma de pagamento com código %d", id));
    }
}
