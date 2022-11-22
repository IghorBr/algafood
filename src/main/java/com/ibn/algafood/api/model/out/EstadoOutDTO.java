package com.ibn.algafood.api.model.out;

import com.ibn.algafood.core.validation.Groups;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class EstadoOutDTO {

    @NotNull(groups = Groups.CadastroCidade.class)
    private Long id;

    @NotBlank(groups = Groups.CadastroEstado.class)
    private String nome;
}
