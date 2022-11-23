package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.ibn.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.repository.CozinhaRepository;
import com.ibn.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public List<Restaurante> findAll(String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }

    public List<Restaurante> consultarPorNome(final String nome, final Long id) {
        return restauranteRepository.consultarPorNome(nome, id);
    }

    public List<Restaurante> find(final String nome, final BigDecimal taxaInicial, final BigDecimal taxaFinal) {
        return restauranteRepository.findCriteriaApi(nome, taxaInicial, taxaFinal);
    }

    public Restaurante findById(final Long id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public Restaurante save(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.ativar();

        // não é necessário fazer isso, mas para deixar explicito
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.inativar();

        // não é necessário fazer isso, mas para deixar explicito
        restauranteRepository.save(restaurante);
    }
}
