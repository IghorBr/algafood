package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.UsuarioMapper;
import com.ibn.algafood.api.model.in.SenhaInputDTO;
import com.ibn.algafood.api.model.in.UsuarioComSenhaInputDTO;
import com.ibn.algafood.api.model.in.UsuarioInputDTO;
import com.ibn.algafood.api.model.out.UsuarioOutDTO;
import com.ibn.algafood.core.security.CheckSecurity;
import com.ibn.algafood.domain.model.Usuario;
import com.ibn.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public ResponseEntity<List<UsuarioOutDTO>> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();

        return new ResponseEntity<>(usuarioMapper.listDomainToDto(usuarios), null, HttpStatus.OK);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutDTO> findById(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);

        return ResponseEntity.ok(usuarioMapper.domainToDto(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioOutDTO> save(@RequestBody @Valid UsuarioComSenhaInputDTO inputDTO) {
        Usuario usuario = usuarioMapper.inputDtoToDomain(inputDTO);

        usuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.domainToDto(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioOutDTO> updateById(@PathVariable("id") Long id,
                                                    @RequestBody @Valid UsuarioInputDTO inputDTO) {
        Usuario usuario = usuarioService.findById(id);
        usuarioMapper.copyToDomainObject(inputDTO, usuario);

        usuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.domainToDto(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> changePassword(@PathVariable("id") Long id,
                                                        @RequestBody @Valid SenhaInputDTO inputDTO) {
        usuarioService.changePassword(inputDTO.getSenhaAtual(), inputDTO.getNovaSenha(), id);
        return ResponseEntity.noContent().build();
    }
}
