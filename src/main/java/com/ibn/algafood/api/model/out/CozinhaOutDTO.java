package com.ibn.algafood.api.model.out;

import com.fasterxml.jackson.annotation.JsonView;
import com.ibn.algafood.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CozinhaOutDTO {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
