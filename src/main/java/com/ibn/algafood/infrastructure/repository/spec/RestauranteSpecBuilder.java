package com.ibn.algafood.infrastructure.repository.spec;

import com.ibn.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestauranteSpecBuilder {

    private RestauranteSpecBuilder() {

    }

    public static Specification<Restaurante> comFreteGratis() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO));
    }

    public static Specification<Restaurante> nomeSemelhante(String nome) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
    }
}
