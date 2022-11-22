package com.ibn.algafood.api.assembler;

import com.ibn.algafood.api.model.out.CidadeOutDTO;
import com.ibn.algafood.domain.model.Cidade;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CidadeDTOAssembler {

    private final ModelMapper mapper;

    public CidadeOutDTO domainToDto(Cidade cidade) {
        return mapper.map(cidade, CidadeOutDTO.class);
    }

    public List<CidadeOutDTO> domainListToDto(List<Cidade> cidades) {
        return cidades.stream().map(c -> this.domainToDto(c)).toList();
    }
}
