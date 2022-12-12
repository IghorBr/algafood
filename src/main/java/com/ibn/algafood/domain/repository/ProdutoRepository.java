package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.Produto;
import com.ibn.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByRestaurante(Restaurante r);
    @Query(value = "FROM Produto p WHERE p.ativo = TRUE AND p.restaurante = ?1")
    List<Produto> findProdutosAtivos(Restaurante r);
    Optional<Produto> findByIdAndRestaurante(Long id, Restaurante restaurante);
}
