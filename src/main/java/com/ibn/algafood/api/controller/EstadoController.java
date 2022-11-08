package com.ibn.algafood.api.controller;

import com.ibn.algafood.core.validation.Groups;
import com.ibn.algafood.domain.model.Estado;
import com.ibn.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    @GetMapping
    public ResponseEntity<List<Estado>> findAll() {
        return ResponseEntity.ok(estadoService.findAll());
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> findById(@PathVariable Long estadoId) {
        Estado estado = estadoService.findById(estadoId);
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<Estado> save(@RequestBody @Validated(Groups.CadastroEstado.class) Estado estado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.save(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> update(@PathVariable Long estadoId,
                                            @RequestBody @Validated(Groups.CadastroEstado.class) Estado estado) {
        Estado estadoAtual = estadoService.findById(estadoId);

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoAtual = estadoService.save(estadoAtual);
        return ResponseEntity.ok(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Object> remover(@PathVariable Long estadoId) {
        estadoService.deleteById(estadoId);
        return ResponseEntity.noContent().build();
    }
}
