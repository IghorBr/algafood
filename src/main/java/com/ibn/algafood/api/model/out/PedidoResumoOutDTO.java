package com.ibn.algafood.api.model.out;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("pedidoFilter")
@Getter @Setter
public class PedidoResumoOutDTO {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestautanteOutDTO restaurante;
    private UsuarioOutDTO cliente;

    @Getter @Setter
    public static class RestautanteOutDTO {
        private Long id;
        private String nome;
    }
}
