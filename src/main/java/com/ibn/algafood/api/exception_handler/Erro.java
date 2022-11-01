package com.ibn.algafood.api.exception_handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Erro {

    private LocalDateTime dataHora;
    private String mensagem;
}
