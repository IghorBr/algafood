package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
