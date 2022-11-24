package com.ibn.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UsuarioInputDTO {

    @NotBlank
    public String nome;

    @NotBlank
    @Email
    public String email;
}
