package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.ibn.algafood.domain.model.Permissao;
import com.ibn.algafood.domain.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    public Permissao findById(Long id) {
        return this.permissaoRepository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }
}
