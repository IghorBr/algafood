package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.GrupoMapper;
import com.ibn.algafood.api.model.in.GrupoInputDTO;
import com.ibn.algafood.api.model.out.GrupoOutDTO;
import com.ibn.algafood.core.security.CheckSecurity;
import com.ibn.algafood.core.validation.Groups;
import com.ibn.algafood.domain.model.Grupo;
import com.ibn.algafood.domain.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService grupoService;
    private final GrupoMapper grupoMapper;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public ResponseEntity<List<GrupoOutDTO>> findAll() {
        List<Grupo> grupos = grupoService.findAll();

        return ResponseEntity.ok(grupoMapper.domainListToDto(grupos));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{id}")
    public ResponseEntity<GrupoOutDTO> findById(@PathVariable("id") Long id) {
        Grupo grupo = grupoService.findBydId(id);

        return ResponseEntity.ok(grupoMapper.domainToDto(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping
    public ResponseEntity<GrupoOutDTO> save(@RequestBody @Validated(Groups.CadastroGrupo.class) GrupoInputDTO inputDTO) {
        Grupo grupo = grupoMapper.inputDtoToDomain(inputDTO);
        grupo = grupoService.save(grupo);

        return ResponseEntity.status(HttpStatus.CREATED).body(grupoMapper.domainToDto(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{id}")
    public ResponseEntity<GrupoOutDTO> updateById(@PathVariable("id") Long id,
                                                  @RequestBody @Validated(Groups.CadastroGrupo.class) GrupoInputDTO inputDTO) {
        Grupo grupo = grupoService.findBydId(id);
        grupoMapper.copyToDomainObject(inputDTO, grupo);

        grupo = grupoService.save(grupo);
        return ResponseEntity.ok(grupoMapper.domainToDto(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        grupoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
