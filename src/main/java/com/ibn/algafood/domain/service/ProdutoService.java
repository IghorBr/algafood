package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.ibn.algafood.domain.model.Produto;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteService restauranteService;

    public List<Produto> findAll(Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return produtoRepository.findByRestaurante(restaurante);
    }

    public Produto findById(Long produtoId, Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return produtoRepository.findByIdAndRestaurante(produtoId, restaurante)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restaurante.getNome()));
    }

    @Transactional
    public Produto save(Long restauranteId, Produto produto) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        produto.setRestaurante(restaurante);
        produto = produtoRepository.save(produto);

        restaurante.adicionarProduto(produto);

        return produto;
    }
}
