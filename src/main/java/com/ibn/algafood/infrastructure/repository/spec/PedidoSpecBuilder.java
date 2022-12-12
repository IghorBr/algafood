package com.ibn.algafood.infrastructure.repository.spec;

import com.ibn.algafood.domain.model.Pedido;
import com.ibn.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class PedidoSpecBuilder {

    private PedidoSpecBuilder() {

    }

    public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            var predicates = new ArrayList<Predicate>();

            if (nonNull(filter.getClienteId()))
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filter.getClienteId()));

            if (nonNull(filter.getRestauranteId()))
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filter.getRestauranteId()));

            if (nonNull(filter.getDataCriacaoInicio()))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));

            if (nonNull(filter.getDataCriacaoFim()))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
