package com.ibn.algafood.api.model.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CidadeOutDTO {

    private Long id;
    private String nome;
    private EstadoOutDTO estado;
}
