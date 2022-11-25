package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.PedidoMapper;
import com.ibn.algafood.api.model.out.PedidoOutDTO;
import com.ibn.algafood.domain.model.Pedido;
import com.ibn.algafood.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @GetMapping
    public ResponseEntity<List<PedidoOutDTO>> findAll() {
        List<Pedido> pedidos = this.pedidoService.findAll();
        List<PedidoOutDTO> pedidoOutDTOS = pedidoMapper.domainListToDto(pedidos);

        return ResponseEntity.ok(pedidoOutDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoOutDTO> findById(@PathVariable("id") Long id) {
        Pedido pedido = this.pedidoService.findById(id);

        return ResponseEntity.ok(pedidoMapper.domaintToDto(pedido));
    }
}
