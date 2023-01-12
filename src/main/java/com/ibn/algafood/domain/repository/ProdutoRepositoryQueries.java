package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto fotoProduto);
    void delete(FotoProduto foto);
}
