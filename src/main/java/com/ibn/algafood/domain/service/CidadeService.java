package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.CidadeNaoEncontradaException;
import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.model.Cidade;
import com.ibn.algafood.domain.model.Estado;
import com.ibn.algafood.domain.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoService estadoService;

    public List<Cidade> findAll() {
        return cidadeRepository.findAll();
    }

    public Cidade findById(final Long id) {
        return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    @Transactional
    public Cidade save(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoService.findById(estadoId);

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void deleteById(final Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso", id));
        }
    }
}
