package com.ibn.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter @Setter
public class PedidoOutDTO {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestautanteOutDTO restaurante;
    private UsuarioOutDTO cliente;
    private FormaPagamentoOutDTO formaPagamento;
    private EnderecoOutDTO enderecoEntrega;
    private List<ItemPedidoOutDTO> itens;

    @Getter @Setter
    public static class RestautanteOutDTO {
        private Long id;
        private String nome;
    }
}
