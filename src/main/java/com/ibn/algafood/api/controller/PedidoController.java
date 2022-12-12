package com.ibn.algafood.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ibn.algafood.api.mapper.PedidoMapper;
import com.ibn.algafood.api.model.in.PedidoInputDTO;
import com.ibn.algafood.api.model.out.PedidoOutDTO;
import com.ibn.algafood.api.model.out.PedidoResumoOutDTO;
import com.ibn.algafood.domain.model.Pedido;
import com.ibn.algafood.domain.repository.filter.PedidoFilter;
import com.ibn.algafood.domain.service.PedidoService;
import com.ibn.algafood.infrastructure.repository.spec.PedidoSpecBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

//    @GetMapping
//    public ResponseEntity<MappingJacksonValue> findAll(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = this.pedidoService.findAll();
//        List<PedidoResumoOutDTO> dtos = pedidoMapper.domainListToResumo(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(dtos);
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.hasText(campos)) {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return ResponseEntity.ok(pedidosWrapper);
//    }

    @GetMapping
    public ResponseEntity<List<PedidoResumoOutDTO>> findAll(PedidoFilter filter) {
        List<Pedido> pedidos = this.pedidoService.findAll(PedidoSpecBuilder.usandoFiltro(filter));
        List<PedidoResumoOutDTO> dtos = pedidoMapper.domainListToResumo(pedidos);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoOutDTO> findById(@PathVariable("id") String id) {
        Pedido pedido = this.pedidoService.findByCodigo(id);

        return ResponseEntity.ok(pedidoMapper.domaintToDto(pedido));
    }

    @PostMapping
    public ResponseEntity<PedidoOutDTO> save(@RequestBody @Valid PedidoInputDTO dto) {

        Pedido pedido = pedidoMapper.inputDtoToDomain(dto);

        pedido = pedidoService.save(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.domaintToDto(pedido));
    }
}
