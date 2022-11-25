package com.ibn.algafood.api.model.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class RestauranteOutDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;
    private CozinhaOutDTO cozinha;
    private EnderecoOutDTO endereco;
}
