package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.validation.Groups;
import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.ibn.algafood.domain.model.Cidade;
import com.ibn.algafood.domain.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> findAll() {
        return ResponseEntity.ok(cidadeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> findById(@PathVariable("id") Long id) {
        Cidade cidade = cidadeService.findById(id);

        return ResponseEntity.ok(cidade);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Validated(Groups.CadastroCidade.class) Cidade cidade) {
        try {
            cidade = cidadeService.save(cidade);

            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody @Validated(Groups.CadastroCidade.class) Cidade cidade) {
        Cidade novaCidade = cidadeService.findById(id);
        BeanUtils.copyProperties(cidade, novaCidade, "id");

        try {
            novaCidade = cidadeService.save(novaCidade);
            return ResponseEntity.ok(novaCidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new EntidadeNaoEncontradaException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        cidadeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
