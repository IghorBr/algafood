package com.ibn.algafood.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CidadeResumoOutDTO {

    private Long id;
    private String nome;

    @JsonProperty("estado")
    private String nomeEstado;
}
