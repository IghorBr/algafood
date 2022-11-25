package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.out.PermissaoOutDTO;
import com.ibn.algafood.domain.model.Permissao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissaoMapper {

    private final ModelMapper mapper;

    public PermissaoOutDTO domainToDto(Permissao permissao) {
        return mapper.map(permissao, PermissaoOutDTO.class);
    }

    public List<PermissaoOutDTO> domainListToDto(List<Permissao> permissoes) {
        return permissoes.stream().map(p -> this.domainToDto(p)).toList();
    }
}
