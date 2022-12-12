package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    @Query("FROM Pedido p JOIN FETCH p.cliente c JOIN FETCH p.restaurante r JOIN FETCH r.cozinha cozinha")
    List<Pedido> findAll();

    Optional<Pedido> findByCodigo(String codigo);
}
