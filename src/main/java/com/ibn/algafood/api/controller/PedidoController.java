package com.ibn.algafood.api.controller;

import com.google.common.collect.ImmutableMap;
import com.ibn.algafood.api.mapper.PedidoMapper;
import com.ibn.algafood.api.model.in.PedidoInputDTO;
import com.ibn.algafood.api.model.out.PedidoOutDTO;
import com.ibn.algafood.api.model.out.PedidoResumoOutDTO;
import com.ibn.algafood.core.page.PageableTranslator;
import com.ibn.algafood.core.security.CheckSecurity;
import com.ibn.algafood.domain.model.Pedido;
import com.ibn.algafood.domain.filter.PedidoFilter;
import com.ibn.algafood.domain.service.PedidoService;
import com.ibn.algafood.infrastructure.repository.spec.PedidoSpecBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @CheckSecurity.Pedidos.PodePesquisar
    @GetMapping
    public ResponseEntity<Page<PedidoResumoOutDTO>> findAll(PedidoFilter filter, Pageable pageable) {
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidos = this.pedidoService.findAll(PedidoSpecBuilder.usandoFiltro(filter), pageable);
        List<PedidoResumoOutDTO> dtos = pedidoMapper.domainListToResumo(pedidos.getContent());

        PageImpl<PedidoResumoOutDTO> page = new PageImpl<>(dtos, pageable, pedidos.getTotalElements());

        return ResponseEntity.ok(page);
    }

    @CheckSecurity.Pedidos.PodeCriar
    @GetMapping("/{id}")
    public ResponseEntity<PedidoOutDTO> findById(@PathVariable("id") String id) {
        Pedido pedido = this.pedidoService.findByCodigo(id);

        return ResponseEntity.ok(pedidoMapper.domaintToDto(pedido));
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @PostMapping
    public ResponseEntity<PedidoOutDTO> save(@RequestBody @Valid PedidoInputDTO dto) {

        Pedido pedido = pedidoMapper.inputDtoToDomain(dto);

        pedido = pedidoService.save(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.domaintToDto(pedido));
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "resturante.nome",
                "nomeCliente", "cliente.nome"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }
}
