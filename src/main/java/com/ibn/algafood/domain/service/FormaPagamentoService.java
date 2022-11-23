package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.ibn.algafood.domain.model.FormaPagamento;
import com.ibn.algafood.domain.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> findAll() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento findById(Long id) {
        return formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public FormaPagamento save(FormaPagamento formaPagamento) {
        return this.formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            this.formaPagamentoRepository.deleteById(id);
            this.formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Forma de Pagamento de código %d não pode ser removida, pois está em uso", id));
        }
    }
}

