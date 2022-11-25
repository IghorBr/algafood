package com.ibn.algafood.domain.model;

import com.ibn.algafood.core.validation.FreteGratisDescricao;
import com.ibn.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Getter @Setter
@FreteGratisDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis", groups = Groups.CadastroRestaurante.class)
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = Groups.CadastroRestaurante.class)
    @Column(nullable = false)
    private String nome;

    @NotNull(groups = Groups.CadastroRestaurante.class)
//    @DecimalMin("0")
    @PositiveOrZero(groups = Groups.CadastroRestaurante.class)
//    @TaxaFrete(groups = Groups.CadastroRestaurante.class)
//    @Multiplo(numero = 5, groups = Groups.CadastroRestaurante.class)
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @Valid
//    @ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
    @NotNull(groups = Groups.CadastroRestaurante.class)
    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp
    private OffsetDateTime dataCadastro;

    @Column(nullable = false, columnDefinition = "datetime")
    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;

    private Boolean ativo = Boolean.TRUE;
    private Boolean aberto = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "RESTAURANTE_FORMA_PAGAMENTO",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos;

    @ManyToMany
    @JoinTable(name = "RESTAURANTE_USUARIO_RESPONSAVEL",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
        this.setAtivo(true);
    }

    public void inativar() {
        this.setAtivo(false);
    }

    public void abrir() {
        this.setAberto(true);
    }

    public void fechar() {
        this.setAberto(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurante that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Restaurante adicionarFormaPagamento(FormaPagamento fp) {
        this.getFormasPagamento().add(fp);
        return this;
    }

    public Restaurante removerFormaPagamento(FormaPagamento fp) {
        this.getFormasPagamento().remove(fp);
        return this;
    }

    public Restaurante adicionarProduto(Produto p) {
        this.getProdutos().add(p);
        return this;
    }

    public Restaurante adicionarResponsavel(Usuario usuario) {
        this.getResponsaveis().add(usuario);
        return this;
    }

    public Restaurante removerResponsavel(Usuario usuario) {
        this.getResponsaveis().remove(usuario);
        return this;
    }

    public boolean verificaFormaPagamento(FormaPagamento formaPagamento) {
        return this.getFormasPagamento().contains(formaPagamento);
    }
}
