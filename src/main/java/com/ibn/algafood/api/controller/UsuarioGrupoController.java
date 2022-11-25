package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.GrupoMapper;
import com.ibn.algafood.api.model.out.GrupoOutDTO;
import com.ibn.algafood.domain.model.Grupo;
import com.ibn.algafood.domain.model.Usuario;
import com.ibn.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
@RequiredArgsConstructor
public class UsuarioGrupoController {

    private final UsuarioService usuarioService;
    private final GrupoMapper grupoMapper;

    @GetMapping
    public ResponseEntity<List<GrupoOutDTO>> findAllGrupos(@PathVariable("usuarioId") Long id) {
        Usuario usuario = usuarioService.findById(id);
        List<Grupo> grupos = usuario.getGrupos().stream().toList();

        return ResponseEntity.ok(grupoMapper.domainListToDto(grupos));
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> adicionarGrupo(@PathVariable("usuarioId") Long usuarioId,
                                               @PathVariable("grupoId") Long grupoId) {
        usuarioService.adicionarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> removerGrupo(@PathVariable("usuarioId") Long usuarioId,
                                               @PathVariable("grupoId") Long grupoId) {
        usuarioService.removerGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }
}
