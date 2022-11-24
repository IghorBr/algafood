package com.ibn.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class UsuarioComSenhaInputDTO extends UsuarioInputDTO {

    @NotBlank
    @Size(min = 3, max = 16)
    private String senha;
}
