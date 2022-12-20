package com.ibn.algafood.api.controller;

import com.ibn.algafood.domain.filter.VendaDiariaFilter;
import com.ibn.algafood.domain.model.dto.VendaDiaria;
import com.ibn.algafood.domain.service.VendaQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public ResponseEntity<List<VendaDiaria>> consultarVendasDiarias(VendaDiariaFilter filter,
                                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        List<VendaDiaria> vendaDiarias = vendaQueryService.consultarVendasDiarias(filter, timeOffset);

        return ResponseEntity.ok(vendaDiarias);
    }
}
