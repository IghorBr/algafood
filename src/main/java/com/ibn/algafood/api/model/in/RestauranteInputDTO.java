package com.ibn.algafood.api.model.in;

import com.ibn.algafood.api.model.out.CidadeOutDTO;
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

    @Valid
    @NotNull(groups = Groups.CadastroRestaurante.class)
    private EnderecoInputDTO endereco;

    @Getter @Setter
    public static class CozinhaInputDTO {

        @NotNull(groups = Groups.CadastroRestaurante.class)
        private Long id;
    }

    @Getter @Setter
    public static class EnderecoInputDTO {

        @NotBlank(groups = Groups.CadastroRestaurante.class)
        private String cep;

        @NotBlank(groups = Groups.CadastroRestaurante.class)
        private String logradouro;

        @NotBlank(groups = Groups.CadastroRestaurante.class)
        private String numero;
        private String complemento;

        @NotBlank(groups = Groups.CadastroRestaurante.class)
        private String bairro;

        @Valid
        @NotNull(groups = Groups.CadastroRestaurante.class)
        private CidadeInputDTO cidade;

        @Getter @Setter
        public static class CidadeInputDTO {

            @NotNull(groups = Groups.CadastroRestaurante.class)
            private Long id;
        }
    }
}
