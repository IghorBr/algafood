package com.ibn.algafood.api.controller;

import com.ibn.algafood.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
@RequiredArgsConstructor
public class StatusPedidoController {

    private final PedidoService pedidoService;

    @PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable("pedidoId") Long pedidoId) {
        pedidoService.confimar(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable("pedidoId") Long pedidoId) {
        pedidoService.entregar(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable("pedidoId") Long pedidoId) {
        pedidoService.cancelar(pedidoId);
        return ResponseEntity.noContent().build();
    }
}
