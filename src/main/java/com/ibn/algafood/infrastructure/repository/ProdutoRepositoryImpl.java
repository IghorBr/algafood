package com.ibn.algafood.infrastructure.repository;

import com.ibn.algafood.domain.model.FotoProduto;
import com.ibn.algafood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return entityManager.merge(fotoProduto);
    }

    @Override
    public void delete(FotoProduto foto) {
        entityManager.remove(foto);
    }
}
