package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.out.PedidoOutDTO;
import com.ibn.algafood.api.model.out.PedidoResumoOutDTO;
import com.ibn.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoMapper {

    private final ModelMapper mapper;

    public PedidoOutDTO domaintToDto(Pedido pedido) {
        return mapper.map(pedido, PedidoOutDTO.class);
    }

    public List<PedidoOutDTO> domainListToDto(List<Pedido> pedidos) {
        return pedidos.stream().map(p -> this.domaintToDto(p)).toList();
    }

    public PedidoResumoOutDTO domainToResumo(Pedido pedido) {
        return this.mapper.map(pedido, PedidoResumoOutDTO.class);
    }

    public List<PedidoResumoOutDTO> domainListToResumo(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> this.domainToResumo(pedido)).toList();
    }
}
