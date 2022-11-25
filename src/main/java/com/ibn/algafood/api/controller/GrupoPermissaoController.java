package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.PermissaoMapper;
import com.ibn.algafood.api.model.out.PermissaoOutDTO;
import com.ibn.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.ibn.algafood.domain.model.Grupo;
import com.ibn.algafood.domain.model.Permissao;
import com.ibn.algafood.domain.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@RequiredArgsConstructor
public class GrupoPermissaoController {

    private final GrupoService grupoService;
    private final PermissaoMapper permissaoMapper;

    @GetMapping
    public ResponseEntity<List<PermissaoOutDTO>> findAllPermissoes(@PathVariable("grupoId") Long id) {
        Grupo grupo = grupoService.findBydId(id);
        List<Permissao> permissoes = grupo.getPermissoes().stream().toList();

        return ResponseEntity.ok(permissaoMapper.domainListToDto(permissoes));
    }

    @GetMapping("/{permissaoId}")
    public ResponseEntity<PermissaoOutDTO> findPermissaoById(@PathVariable("grupoId") Long grupoId,
                                                             @PathVariable("permissaoId") Long permissaoId) {
        Grupo grupo = grupoService.findBydId(grupoId);
        Optional<Permissao> optionalPermissao = grupo.getPermissoes().stream().filter(p -> p.getId().compareTo(permissaoId) == 0).findFirst();

        Permissao permissao = optionalPermissao.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId, grupo.getNome()));

        return ResponseEntity.ok(permissaoMapper.domainToDto(permissao));
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> adicionarPermissao(@PathVariable("grupoId") Long grupoId,
                                                   @PathVariable("permissaoId") Long permissaoId) {
        grupoService.adicionarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> removerPermissao(@PathVariable("grupoId") Long grupoId,
                                                 @PathVariable("permissaoId") Long permissaoId) {
        grupoService.removerPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
