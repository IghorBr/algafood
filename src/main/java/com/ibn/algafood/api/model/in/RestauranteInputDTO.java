package com.ibn.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter @Setter
public class RestauranteInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaInputDTO cozinha;

    @Valid
    @NotNull
    private EnderecoInputDTO endereco;

    @Getter @Setter
    public static class CozinhaInputDTO {

        @NotNull
        private Long id;
    }
}
