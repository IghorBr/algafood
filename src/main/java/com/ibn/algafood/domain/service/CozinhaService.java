package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public List<Cozinha> findAll() {
        return cozinhaRepository.findAll();
    }

    public Cozinha findById(final Long id) {
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    @Transactional
    public Cozinha save(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void deleteById(final Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
        }
    }
}
