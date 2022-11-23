package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.ibn.algafood.domain.model.Estado;
import com.ibn.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado findById(final Long id) {
        return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    @Transactional
    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public void deleteById(final Long id) {
        try {
            estadoRepository.deleteById(id);
            estadoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso", id));
        }
    }
}
