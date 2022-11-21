package com.ibn.algafood.api.model.in;

import com.ibn.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CozinhaInputDTO {

    @NotNull(groups = Groups.CadastroRestaurante.class)
    private Long id;
}
