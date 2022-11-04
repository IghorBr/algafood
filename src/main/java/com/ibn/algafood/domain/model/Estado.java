package com.ibn.algafood.domain.model;

import com.ibn.algafood.api.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter @Setter
public class Estado {

    @NotNull(groups = Groups.CadastroCidade.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = Groups.CadastroEstado.class)
    @Column(nullable = false)
    private String nome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estado estado)) return false;
        return getId().equals(estado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
