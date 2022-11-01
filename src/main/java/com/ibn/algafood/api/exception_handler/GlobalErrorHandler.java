package com.ibn.algafood.api.exception_handler;

import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Erro> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        Erro erro = Erro.builder().dataHora(LocalDateTime.now()).mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(AlgafoodException.class)
    public ResponseEntity<Erro> handleAlgafoodException(AlgafoodException e) {
        Erro erro = Erro.builder().dataHora(LocalDateTime.now()).mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
