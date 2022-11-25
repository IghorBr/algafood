package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.GrupoNaoEncontradoException;
import com.ibn.algafood.domain.model.Grupo;
import com.ibn.algafood.domain.model.Permissao;
import com.ibn.algafood.domain.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final PermissaoService permissaoService;

    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    public Grupo findBydId(Long id) {
        return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            grupoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(e.getMessage());
        }
    }

    @Transactional
    public void adicionarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = this.findBydId(grupoId);
        Permissao permissao = this.permissaoService.findById(permissaoId);

        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void removerPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = this.findBydId(grupoId);
        Permissao permissao = this.permissaoService.findById(permissaoId);

        grupo.removerPermissao(permissao);
    }
}
