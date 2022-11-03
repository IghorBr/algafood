package com.ibn.algafood.api.exception_handler;

import lombok.Getter;

@Getter
public enum ErrorType {

    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
    ALGAFOOD_EXCEPTION("Violação da regra de negócio", "/erro-negocio"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    JSON_INVALIDO("JSON inválido", "/json-invalido"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido")
    ;

    private final String title;
    private final String uri;

    ErrorType(String title, String path) {
        this.title = title;
        this.uri = "https://algafood.com.br" + path;
    }
}
