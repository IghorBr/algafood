package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.assembler.CidadeDTOAssembler;
import com.ibn.algafood.api.model.out.CidadeOutDTO;
import com.ibn.algafood.core.validation.Groups;
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
    private final CidadeDTOAssembler cidadeAssembler;

    @GetMapping
    public ResponseEntity<List<CidadeOutDTO>> findAll() {
        List<Cidade> cidades = cidadeService.findAll();

        return ResponseEntity.ok(cidadeAssembler.domainListToDto(cidades));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeOutDTO> findById(@PathVariable("id") Long id) {
        Cidade cidade = cidadeService.findById(id);

        return ResponseEntity.ok(cidadeAssembler.domainToDto(cidade));
    }

    @PostMapping
    public ResponseEntity<CidadeOutDTO> save(@RequestBody @Validated(Groups.CadastroCidade.class) Cidade cidade) {
        try {
            cidade = cidadeService.save(cidade);

            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeAssembler.domainToDto(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeOutDTO> update(@PathVariable("id") Long id, @RequestBody @Validated(Groups.CadastroCidade.class) Cidade cidade) {
        Cidade novaCidade = cidadeService.findById(id);
        BeanUtils.copyProperties(cidade, novaCidade, "id");

        try {
            novaCidade = cidadeService.save(novaCidade);
            return ResponseEntity.ok(cidadeAssembler.domainToDto(novaCidade));
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
