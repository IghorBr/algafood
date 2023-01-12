package com.ibn.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FotoProdutoOutDTO {

    private Long id;
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
