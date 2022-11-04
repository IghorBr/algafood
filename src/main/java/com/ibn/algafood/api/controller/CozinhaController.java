package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.validation.Groups;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
@RequiredArgsConstructor
public class CozinhaController {

    private final CozinhaService cozinhaService;

    @GetMapping
    public ResponseEntity<List<Cozinha>> findAll() {
        return ResponseEntity.ok(cozinhaService.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> findById(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaService.findById(cozinhaId);

        return ResponseEntity.ok(cozinha);
    }

    @PostMapping
    public ResponseEntity<Cozinha> save(@RequestBody @Validated(Groups.CadastroCozinha.class) Cozinha cozinha) {
        cozinha = cozinhaService.save(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> update(@PathVariable Long cozinhaId,
                                             @RequestBody @Validated(Groups.CadastroCozinha.class) Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaService.findById(cozinhaId);

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        Cozinha cozinhaSalva = cozinhaService.save(cozinhaAtual);
        return ResponseEntity.ok(cozinhaSalva);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long cozinhaId) {
        cozinhaService.deleteById(cozinhaId);
    }
}
