package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.repository.CozinhaRepository;
import com.ibn.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public List<Restaurante> consultarPorNome(final String nome, final Long id) {
        return restauranteRepository.consultarPorNome(nome, id);
    }

    public List<Restaurante> find(final String nome, final BigDecimal taxaInicial, final BigDecimal taxaFinal) {
        return restauranteRepository.findCriteriaApi(nome, taxaInicial, taxaFinal);
    }

    public Optional<Restaurante> findById(final Long id) {
        return restauranteRepository.findById(id);
    }

    public Restaurante save(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }
}
