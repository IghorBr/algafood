package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.PedidoNaoEncontradoException;
import com.ibn.algafood.domain.model.Pedido;
import com.ibn.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return this.pedidoRepository.findAll();
    }

    public Pedido findById(Long id) {
        return this.pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }
}
