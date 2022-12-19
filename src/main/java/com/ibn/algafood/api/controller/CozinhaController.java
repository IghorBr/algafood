package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.CozinhaMapper;
import com.ibn.algafood.api.model.in.CozinhaInputDTO;
import com.ibn.algafood.api.model.out.CozinhaOutDTO;
import com.ibn.algafood.core.validation.Groups;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final CozinhaMapper cozinhaMapper;

    @GetMapping
    public ResponseEntity<Page<CozinhaOutDTO>> findAll(Pageable pageable) {
        Page<Cozinha> cozinhas = cozinhaService.findAll(pageable);
        List<CozinhaOutDTO> dtos = cozinhaMapper.domainListToDto(cozinhas.getContent());

        PageImpl<CozinhaOutDTO> cozinhaPage = new PageImpl<>(dtos, pageable, cozinhas.getTotalElements());

        return ResponseEntity.ok(cozinhaPage);
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaOutDTO> findById(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaService.findById(cozinhaId);

        return ResponseEntity.ok(cozinhaMapper.domainToDto(cozinha));
    }

    @PostMapping
    public ResponseEntity<CozinhaOutDTO> save(@RequestBody @Validated(Groups.CadastroCozinha.class) CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinha = cozinhaMapper.inputDtoToDomain(cozinhaInputDTO);

        cozinha = cozinhaService.save(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaMapper.domainToDto(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaOutDTO> update(@PathVariable Long cozinhaId,
                                             @RequestBody @Validated(Groups.CadastroCozinha.class) CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinhaAtual = cozinhaService.findById(cozinhaId);

        cozinhaMapper.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);

//        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        Cozinha cozinhaSalva = cozinhaService.save(cozinhaAtual);
        return ResponseEntity.ok(cozinhaMapper.domainToDto(cozinhaSalva));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long cozinhaId) {
        cozinhaService.deleteById(cozinhaId);
    }
}
