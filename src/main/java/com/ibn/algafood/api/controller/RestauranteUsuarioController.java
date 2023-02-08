package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.UsuarioMapper;
import com.ibn.algafood.api.model.out.UsuarioOutDTO;
import com.ibn.algafood.core.security.CheckSecurity;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.model.Usuario;
import com.ibn.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
@RequiredArgsConstructor
public class RestauranteUsuarioController {

    private final RestauranteService restauranteService;
    private final UsuarioMapper usuarioMapper;

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @GetMapping
    public ResponseEntity<List<UsuarioOutDTO>> findAllResponsaveis(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = this.restauranteService.findById(id);
        List<Usuario> usuarios = restaurante.getResponsaveis().stream().toList();

        return ResponseEntity.ok(usuarioMapper.listDomainToDto(usuarios));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> adicionarResponsavel(@PathVariable("restauranteId") Long restauranteId,
                                                     @PathVariable("usuarioId") Long usuarioId) {
        this.restauranteService.adicionarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> removerResponsavel(@PathVariable("restauranteId") Long restauranteId,
                                                     @PathVariable("usuarioId") Long usuarioId) {
        this.restauranteService.removerResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
