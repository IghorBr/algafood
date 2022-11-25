package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.PedidoMapper;
import com.ibn.algafood.api.model.in.PedidoInputDTO;
import com.ibn.algafood.api.model.out.PedidoOutDTO;
import com.ibn.algafood.api.model.out.PedidoResumoOutDTO;
import com.ibn.algafood.domain.model.Pedido;
import com.ibn.algafood.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @GetMapping
    public ResponseEntity<List<PedidoResumoOutDTO>> findAll() {
        List<Pedido> pedidos = this.pedidoService.findAll();
        List<PedidoResumoOutDTO> dtos = pedidoMapper.domainListToResumo(pedidos);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoOutDTO> findById(@PathVariable("id") Long id) {
        Pedido pedido = this.pedidoService.findById(id);

        return ResponseEntity.ok(pedidoMapper.domaintToDto(pedido));
    }

    @PostMapping
    public ResponseEntity<PedidoOutDTO> save(@RequestBody @Valid PedidoInputDTO dto) {

        Pedido pedido = pedidoMapper.inputDtoToDomain(dto);

        pedido = pedidoService.save(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.domaintToDto(pedido));
    }
}
