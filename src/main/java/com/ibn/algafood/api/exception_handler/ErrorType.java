package com.ibn.algafood.api.exception_handler;

import lombok.Getter;

@Getter
public enum ErrorType {

    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontrada"),
    ALGAFOOD_EXCEPTION("Violação da regra de negócio", "/erro-negocio"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso")
    ;

    private final String title;
    private final String uri;

    ErrorType(String title, String path) {
        this.title = title;
        this.uri = "https://algafood.com.br" + path;
    }
}
