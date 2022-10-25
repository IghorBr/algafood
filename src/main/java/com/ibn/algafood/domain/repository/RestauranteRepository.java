package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestauranteRepository extends BaseRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    @Query("FROM Restaurante r INNER JOIN FETCH r.cozinha LEFT JOIN FETCH r.formasPagamento")
    List<Restaurante> findAll();

    List<Restaurante> consultarPorNome(String nome, Long id);
}
