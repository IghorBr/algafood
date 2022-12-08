package com.ibn.algafood.api.model.out;

import com.fasterxml.jackson.annotation.JsonView;
import com.ibn.algafood.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class RestauranteOutDTO {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaOutDTO cozinha;
    private EnderecoOutDTO endereco;
}
