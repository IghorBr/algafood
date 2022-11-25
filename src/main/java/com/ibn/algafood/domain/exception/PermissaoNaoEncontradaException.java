package com.ibn.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissaoNaoEncontradaException(Long id) {
        this(String.format("Não existe uma permissão com o código %d.", id));
    }

    public PermissaoNaoEncontradaException(Long permissaoId, String grupo) {
        this(String.format("Não existe uma permissão com o código %d associada ao grupo %s", permissaoId, grupo));
    }
}
