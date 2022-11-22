package com.ibn.algafood.api.model.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class RestauranteOutDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaOutDTO cozinha;

    RestauranteOutDTO(Long id, String nome, BigDecimal taxaFrete, CozinhaOutDTO cozinha) {
        this.id = id;
        this.nome = nome;
        this.taxaFrete = taxaFrete;
        this.cozinha = cozinha;
    }

    public static RestauranteOutDTOBuilder builder() {
        return new RestauranteOutDTOBuilder();
    }

    public static class RestauranteOutDTOBuilder {
        private Long id;
        private String nome;
        private BigDecimal taxaFrete;
        private CozinhaOutDTO cozinha;

        RestauranteOutDTOBuilder() {
        }

        public RestauranteOutDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RestauranteOutDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public RestauranteOutDTOBuilder taxaFrete(BigDecimal taxaFrete) {
            this.taxaFrete = taxaFrete;
            return this;
        }

        public RestauranteOutDTOBuilder cozinha(CozinhaOutDTO cozinha) {
            this.cozinha = cozinha;
            return this;
        }

        public RestauranteOutDTO build() {
            return new RestauranteOutDTO(id, nome, taxaFrete, cozinha);
        }

        public String toString() {
            return "RestauranteOutDTO.RestauranteOutDTOBuilder(id=" + this.id + ", nome=" + this.nome + ", taxaFrete=" + this.taxaFrete + ", cozinha=" + this.cozinha + ")";
        }
    }
}
