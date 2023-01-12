package com.ibn.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);

    default void substituir(String nomeArquivo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (Objects.nonNull(nomeArquivo))
            remover(nomeArquivo);
    }

    default String gerarNomeArquivo(String nome) {
        return UUID.randomUUID().toString() + "_" + nome;
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
