package com.ibn.algafood.api.model.in;

import com.ibn.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CidadeInputDTO {

    @NotBlank(groups = Groups.CadastroCidade.class)
    private String nome;

    @Valid
    @NotNull(groups = Groups.CadastroCidade.class)
    private EstadoInputDTO estado;

    @Getter @Setter
    public static class EstadoInputDTO {

        @NotNull(groups = Groups.CadastroCidade.class)
        private Long id;
    }
}
