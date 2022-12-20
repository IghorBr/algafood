package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.filter.VendaDiariaFilter;
import com.ibn.algafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset);
}
