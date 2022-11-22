package com.ibn.algafood.api.model.in;

import com.ibn.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CozinhaInputDTO {

    @NotBlank(groups = Groups.CadastroCozinha.class)
    private String nome;
}
