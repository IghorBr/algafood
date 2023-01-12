package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.FotoNaoEncontradaException;
import com.ibn.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.ibn.algafood.domain.model.FotoProduto;
import com.ibn.algafood.domain.model.Produto;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.repository.ProdutoRepository;
import com.ibn.algafood.infrastructure.service.storage.LocalStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteService restauranteService;

    private final FotoStorageService storageService;

    public ProdutoService(ProdutoRepository produtoRepository, RestauranteService restauranteService, LocalStorageService storageService) {
        this.produtoRepository = produtoRepository;
        this.restauranteService = restauranteService;
        this.storageService = storageService;
    }

    public List<Produto> findAll(Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return produtoRepository.findByRestaurante(restaurante);
    }

    public List<Produto> findAtivos(Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return produtoRepository.findProdutosAtivos(restaurante);
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

    public FotoProduto getFoto(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvarFoto(FotoProduto foto, InputStream inputStream) {
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        String novoNome = storageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeAntigo = null;

        Optional<FotoProduto> fotoProduto = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoProduto.isPresent()) {
            nomeAntigo = fotoProduto.get().getNomeArquivo();
            produtoRepository.delete(fotoProduto.get());
        }
        foto.setNomeArquivo(novoNome);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(inputStream)
                .build();


        storageService.substituir(nomeAntigo, novaFoto);

        return foto;
    }

    @Transactional
    public void excluirFoto(Long restauranteId, Long produtoId) {
        FotoProduto foto = getFoto(restauranteId, produtoId);

        produtoRepository.delete(foto);
        produtoRepository.flush();

        storageService.remover(foto.getNomeArquivo());
    }

}
