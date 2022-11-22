package com.ibn.algafood.api.model.out;

import com.ibn.algafood.core.validation.Groups;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CidadeOutDTO {

    private Long id;

    @NotBlank(groups = Groups.CadastroCidade.class)
    private String nome;

    @Valid
    @NotNull(groups = Groups.CadastroCidade.class)
    private EstadoOutDTO estado;
}
