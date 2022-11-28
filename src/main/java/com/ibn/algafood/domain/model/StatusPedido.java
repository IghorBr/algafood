package com.ibn.algafood.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum StatusPedido {

    CRIADO("Criado", Arrays.asList()),
    CONFIRMADO("Confirmado", Arrays.asList(CRIADO)),
    ENTREGUE("Entregue", Arrays.asList(CONFIRMADO)),
    CANCELADO("Cancelado", Arrays.asList(CRIADO));

    private final String descricao;
    private final List<StatusPedido> statusAnteriores;

    public boolean podeAlterarStatus(StatusPedido novoStatus) {
        return novoStatus.getStatusAnteriores().contains(this);
    }
}
