package com.ibn.algafood.infrastructure.repository;

import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.repository.RestauranteRepository;
import com.ibn.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.ibn.algafood.infrastructure.repository.spec.RestauranteSpecBuilder.comFreteGratis;
import static com.ibn.algafood.infrastructure.repository.spec.RestauranteSpecBuilder.nomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    private final RestauranteRepository restauranteRepository;

    @Lazy
    public RestauranteRepositoryImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

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

    @Override
    public List<Restaurante> findCriteriaApi(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteriaQuery = builder.createQuery(Restaurante.class);

        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(nome))
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        if (Objects.nonNull(taxaFreteInicial))
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        if (Objects.nonNull(taxaFreteFinal))
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(nomeSemelhante(nome)));
    }
}
