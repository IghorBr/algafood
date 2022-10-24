package com.ibn.algafood.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FORMA_PAGAMENTO")
@Getter @Setter
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FormaPagamento)) return false;
        final FormaPagamento other = (FormaPagamento) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FormaPagamento;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}
