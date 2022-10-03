package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RestauranteRepository extends BaseRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> consultarPorNome(String nome, Long id);
}
