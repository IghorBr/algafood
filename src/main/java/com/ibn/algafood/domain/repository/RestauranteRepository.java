package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> consultarPorNome(String nome, Long id);
}
