package com.ibn.algafood.infrastructure.service;

import com.ibn.algafood.domain.filter.VendaDiariaFilter;
import com.ibn.algafood.domain.model.dto.VendaDiaria;
import com.ibn.algafood.domain.service.VendaQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class VendaQueryServiceImpl implements VendaQueryService {

    private final EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
        StringBuilder string = new StringBuilder();
        var parametros = new HashMap<String, Object>();

        string.append("SELECT date(p.data_criacao), count(p.id), sum(p.valor_total) FROM pedido p WHERE 1=1 AND p.status in ('ENTREGUE', 'CONFIRMADO') ");

        if (nonNull(filter.getRestauranteId())) {
            string.append(" AND p.restaurante_id = :restauranteId");
            parametros.put("restauranteId", filter.getRestauranteId());
        }

        if (nonNull(filter.getDataCriacaoInicio())) {
            string.append(" AND p.data_criacao >= :dataInicio ");
            parametros.put("dataInicio", filter.getDataCriacaoInicio());
        }

        if (nonNull(filter.getDataCriacaoFim())) {
            string.append(" AND p.data_criacao <= :dataFim ");
            parametros.put("dataFim", filter.getDataCriacaoFim());
        }

        string.append(" GROUP BY date(p.data_criacao)");

        Query nativeQuery = entityManager.createNativeQuery(string.toString());

        parametros.forEach((key, value) -> {
            nativeQuery.setParameter(key, value);
        });

        List<Object[]> resultList = nativeQuery.getResultList();

        return resultList.stream().map(rl -> {
            var date = ((Date) rl[0]).toLocalDate();

            return new VendaDiaria(date, ((BigInteger) rl[1]).longValue(), (BigDecimal) rl[2]);
        }).toList();
    }
}
