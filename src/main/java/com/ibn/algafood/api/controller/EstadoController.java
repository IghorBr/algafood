package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.EstadoMapper;
import com.ibn.algafood.api.model.in.EstadoInputDTO;
import com.ibn.algafood.api.model.out.EstadoOutDTO;
import com.ibn.algafood.core.validation.Groups;
import com.ibn.algafood.domain.model.Estado;
import com.ibn.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;
    private final EstadoMapper estadoAssembler;

    @GetMapping
    public ResponseEntity<List<EstadoOutDTO>> findAll() {
        List<Estado> estados = estadoService.findAll();
        return ResponseEntity.ok(estadoAssembler.domainListToDto(estados));
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoOutDTO> findById(@PathVariable Long estadoId) {
        Estado estado = estadoService.findById(estadoId);
        return ResponseEntity.ok(estadoAssembler.domainToDto(estado));
    }

    @PostMapping
    public ResponseEntity<EstadoOutDTO> save(@RequestBody @Validated(Groups.CadastroEstado.class) EstadoInputDTO estadoInputDTO) {
        Estado estado = estadoAssembler.inputDtoToDomain(estadoInputDTO);

        estado = estadoService.save(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoAssembler.domainToDto(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoOutDTO> update(@PathVariable Long estadoId,
                                            @RequestBody @Validated(Groups.CadastroEstado.class) EstadoInputDTO estadoInputDTO) {
        Estado estadoAtual = estadoService.findById(estadoId);

        estadoAssembler.copyToDomainObject(estadoInputDTO, estadoAtual);

//        BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoAtual = estadoService.save(estadoAtual);
        return ResponseEntity.ok(estadoAssembler.domainToDto(estadoAtual));
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Void> remover(@PathVariable Long estadoId) {
        estadoService.deleteById(estadoId);
        return ResponseEntity.noContent().build();
    }
}
