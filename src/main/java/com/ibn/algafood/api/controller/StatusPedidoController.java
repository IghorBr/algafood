package com.ibn.algafood.api.controller;

import com.ibn.algafood.core.security.CheckSecurity;
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

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable("pedidoId") String pedidoId) {
        pedidoService.confimar(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable("pedidoId") String pedidoId) {
        pedidoService.entregar(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable("pedidoId") String pedidoId) {
        pedidoService.cancelar(pedidoId);
        return ResponseEntity.noContent().build();
    }
}
