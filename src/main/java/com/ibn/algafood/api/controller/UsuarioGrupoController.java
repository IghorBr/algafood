package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.GrupoMapper;
import com.ibn.algafood.api.model.out.GrupoOutDTO;
import com.ibn.algafood.core.security.CheckSecurity;
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

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public ResponseEntity<List<GrupoOutDTO>> findAllGrupos(@PathVariable("usuarioId") Long id) {
        Usuario usuario = usuarioService.findById(id);
        List<Grupo> grupos = usuario.getGrupos().stream().toList();

        return ResponseEntity.ok(grupoMapper.domainListToDto(grupos));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> adicionarGrupo(@PathVariable("usuarioId") Long usuarioId,
                                               @PathVariable("grupoId") Long grupoId) {
        usuarioService.adicionarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> removerGrupo(@PathVariable("usuarioId") Long usuarioId,
                                               @PathVariable("grupoId") Long grupoId) {
        usuarioService.removerGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }
}
