package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.FormaPagamentoMapper;
import com.ibn.algafood.api.mapper.RestauranteMapper;
import com.ibn.algafood.api.model.out.FormaPagamentoOutDTO;
import com.ibn.algafood.domain.model.FormaPagamento;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@RequiredArgsConstructor
public class RestauranteFormaPagamentoController {

    private final RestauranteService restauranteService;
    private final RestauranteMapper restauranteMapper;
    private final FormaPagamentoMapper formaPagamentoMapper;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutDTO>> findFormasPagamentos(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = restauranteService.findById(id);

        List<FormaPagamento> formasPagamento = restaurante.getFormasPagamento().stream().toList();

        return ResponseEntity.ok(formaPagamentoMapper.domainListToDto(formasPagamento));
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> removerFormaPagamento(@PathVariable("restauranteId") Long restauranteId,
                                                      @PathVariable("formaPagamentoId") Long formaPagamentoId) {
        this.restauranteService.removerFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> adicionarFormaPagamento(@PathVariable("restauranteId") Long restauranteId,
                                                        @PathVariable("formaPagamentoId") Long formaPagamentoId) {
        this.restauranteService.adicionarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
