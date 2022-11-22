package com.ibn.algafood.api.model.out;

import com.ibn.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class CozinhaOutDTO {

    private Long id;


    private String nome;

    CozinhaOutDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static CozinhaOutDTOBuilder builder() {
        return new CozinhaOutDTOBuilder();
    }

    public static class CozinhaOutDTOBuilder {
        private Long id;
        private String nome;

        CozinhaOutDTOBuilder() {
        }

        public CozinhaOutDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CozinhaOutDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public CozinhaOutDTO build() {
            return new CozinhaOutDTO(id, nome);
        }

        public String toString() {
            return "CozinhaOutDTO.CozinhaOutDTOBuilder(id=" + this.id + ", nome=" + this.nome + ")";
        }
    }
}
