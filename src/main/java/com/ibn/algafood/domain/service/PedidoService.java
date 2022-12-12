package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.PedidoNaoEncontradoException;
import com.ibn.algafood.domain.model.*;
import com.ibn.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;
    private final FormaPagamentoService formaPagamentoService;
    private final UsuarioService usuarioService;

    public List<Pedido> findAll() {
        return this.pedidoRepository.findAll();
    }

    public List<Pedido> findAll(Specification<Pedido> specification) {
        return this.pedidoRepository.findAll(specification);
    }

    public Pedido findById(Long id) {
        return this.pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public Pedido findByCodigo(String codigo) {
        return this.pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(String.format("Não exite pedido com código %s.", codigo)));
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        // SETA O STATUS DO PEDIDO
        pedido.setCodigo(UUID.randomUUID().toString());
        pedido.criar();

        Long restauranteId = pedido.getRestaurante().getId();
        Restaurante restaurante = restauranteService.findById(restauranteId);

        // SETA O RESTAURANTE E TAMBEM A TAXA FRETE
        pedido.setRestaurante(restaurante);
        pedido.defineTaxaFrete();

        // CARREGA AS INFORMACOES DOS ITEMS-PEDIDOS
        pedido.getItens().forEach(itemPedido -> {
            Produto produto = produtoService.findById(itemPedido.getProduto().getId(), restauranteId);
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.setPrecoTotal(produto.getPreco().multiply(BigDecimal.valueOf(itemPedido.getQuantidade())));
        });

        pedido.atribuirPedidos();
        pedido.calculaValorTotal();

        // CARREGA A FORMA DE PAGAMENTO
        FormaPagamento formaPagamento = formaPagamentoService.findById(pedido.getFormaPagamento().getId());

        if (!restaurante.verificaFormaPagamento(formaPagamento)) {
            throw new AlgafoodException(String.format("O restaurante de código %d não aceita a forma de pagamento de código %d", restaurante.getId(), formaPagamento.getId()));
        }

        pedido.setFormaPagamento(formaPagamento);

        // CARREGA O USUARIO
        Usuario usuario = this.usuarioService.findById(1L);
        pedido.setCliente(usuario);

        return this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void confimar(String pedidoId) {
        Pedido pedido = this.findByCodigo(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(String pedidoId) {
        Pedido pedido = this.findByCodigo(pedidoId);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String pedidoId) {
        Pedido pedido = this.findByCodigo(pedidoId);
        pedido.cancelar();
    }
}
