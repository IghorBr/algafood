package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.assembler.CozinhaDTOAssembler;
import com.ibn.algafood.api.model.in.CozinhaInputDTO;
import com.ibn.algafood.api.model.out.CozinhaOutDTO;
import com.ibn.algafood.core.validation.Groups;
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
    private final CozinhaDTOAssembler cozinhaAssembler;

    @GetMapping
    public ResponseEntity<List<CozinhaOutDTO>> findAll() {
        List<Cozinha> cozinhas = cozinhaService.findAll();

        return ResponseEntity.ok(cozinhaAssembler.domainListToDto(cozinhas));
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaOutDTO> findById(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaService.findById(cozinhaId);

        return ResponseEntity.ok(cozinhaAssembler.domainToDto(cozinha));
    }

    @PostMapping
    public ResponseEntity<CozinhaOutDTO> save(@RequestBody @Validated(Groups.CadastroCozinha.class) CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinha = cozinhaAssembler.inputDtoToDomain(cozinhaInputDTO);

        cozinha = cozinhaService.save(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaAssembler.domainToDto(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaOutDTO> update(@PathVariable Long cozinhaId,
                                             @RequestBody @Validated(Groups.CadastroCozinha.class) CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinhaAtual = cozinhaService.findById(cozinhaId);

        cozinhaAssembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);

//        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        Cozinha cozinhaSalva = cozinhaService.save(cozinhaAtual);
        return ResponseEntity.ok(cozinhaAssembler.domainToDto(cozinhaSalva));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long cozinhaId) {
        cozinhaService.deleteById(cozinhaId);
    }
}
