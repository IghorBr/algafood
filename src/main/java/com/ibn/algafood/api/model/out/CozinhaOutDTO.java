package com.ibn.algafood.api.model.out;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CozinhaOutDTO {

    private Long id;
    private String nome;
}
