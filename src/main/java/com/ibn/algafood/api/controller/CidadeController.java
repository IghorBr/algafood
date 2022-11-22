package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.assembler.CidadeDTOAssembler;
import com.ibn.algafood.api.model.in.CidadeInputDTO;
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
    public ResponseEntity<CidadeOutDTO> save(@RequestBody @Validated(Groups.CadastroCidade.class) CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = cidadeService.save(
                    cidadeAssembler.inputDtoToDomain(cidadeInputDTO)
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeAssembler.domainToDto(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeOutDTO> update(@PathVariable("id") Long id, @RequestBody @Validated(Groups.CadastroCidade.class) CidadeInputDTO cidadeInput) {
        Cidade cidadeAtual = cidadeService.findById(id);
        cidadeAssembler.copyToDomainObject(cidadeInput, cidadeAtual);

//        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        try {
            cidadeAtual = cidadeService.save(cidadeAtual);
            return ResponseEntity.ok(cidadeAssembler.domainToDto(cidadeAtual));
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
