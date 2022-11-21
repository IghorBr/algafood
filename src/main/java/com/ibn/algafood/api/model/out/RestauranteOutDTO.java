package com.ibn.algafood.api.model.out;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@Builder
public class RestauranteOutDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaOutDTO cozinha;
}
