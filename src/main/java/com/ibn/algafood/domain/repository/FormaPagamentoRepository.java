package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("SELECT MAX(fp.dataAtualizacao) FROM FormaPagamento fp")
    OffsetDateTime getDataUltimaAtualizacao();

    @Query("SELECT fp.dataAtualizacao FROM FormaPagamento fp WHERE fp.id = ?1")
    OffsetDateTime getDataUltimaAtualizacao(Long id);
}
