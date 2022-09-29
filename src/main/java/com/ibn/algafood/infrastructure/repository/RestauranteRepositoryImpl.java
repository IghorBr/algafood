package com.ibn.algafood.infrastructure.repository;

import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = new StringBuilder(" FROM Restaurante r WHERE 1=1 ");

        var parametros = new HashMap<String, Object>();

        if (StringUtils.hasLength(nome)) {
            jpql.append(" AND r.nome LIKE :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (Objects.nonNull(taxaFreteFinal)) {
            jpql.append(" AND r.taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }
        if (Objects.nonNull(taxaFreteInicial)) {
            jpql.append(" AND r.taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((key, value) -> {
            query.setParameter(key, value);
        });

        return query.getResultList();
    }
}
