package com.ibn.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnderecoOutDTO {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeOutDTO cidade;
//    private CidadeResumoOutDTO cidade;
}
