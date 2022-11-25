package com.ibn.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PedidoInputDTO {

    @Valid
    @NotNull
    private RestauranteInputDTO restaurante;

    @Valid
    @NotNull
    private FormaPagamentoInputDTO formaPagamento;

    @Valid
    @NotNull
    private EnderecoInputDTO enderecoEntrega;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInputDTO> itens = new ArrayList<>();

    @Getter @Setter
    public static class RestauranteInputDTO {
        @NotNull
        private Long id;
    }

    @Getter @Setter
    public static class FormaPagamentoInputDTO {
        @NotNull
        private Long id;
    }

    @Getter @Setter
    public static class ItemPedidoInputDTO {
        @NotNull
        private Long produtoId;

        @NotNull
        @Positive
        private Integer quantidade;
        private String observacao;
    }
}
