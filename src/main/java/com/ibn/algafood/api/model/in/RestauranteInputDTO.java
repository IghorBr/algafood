package com.ibn.algafood.api.model.in;

import com.ibn.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter @Setter
public class RestauranteInputDTO {

    @NotBlank(groups = Groups.CadastroRestaurante.class)
    private String nome;

    @PositiveOrZero(groups = Groups.CadastroRestaurante.class)
    private BigDecimal taxaFrete;

    @Valid
    @NotNull(groups = Groups.CadastroRestaurante.class)
    private CozinhaInputDTO cozinha;
}
