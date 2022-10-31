package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.model.Estado;
import com.ibn.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado findById(final Long id) {
        return estadoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Estado não encontrado"));
    }

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void deleteById(final Long id) {
        try {
            estadoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com código %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso", id));
        }
    }
}
