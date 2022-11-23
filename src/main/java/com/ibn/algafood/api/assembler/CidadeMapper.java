package com.ibn.algafood.api.assembler;

import com.ibn.algafood.api.model.in.CidadeInputDTO;
import com.ibn.algafood.api.model.out.CidadeOutDTO;
import com.ibn.algafood.domain.model.Cidade;
import com.ibn.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CidadeMapper {

    private final ModelMapper mapper;

    public CidadeOutDTO domainToDto(Cidade cidade) {
        return mapper.map(cidade, CidadeOutDTO.class);
    }

    public List<CidadeOutDTO> domainListToDto(List<Cidade> cidades) {
        return cidades.stream().map(c -> this.domainToDto(c)).toList();
    }

    public Cidade inputDtoToDomain(CidadeInputDTO input) {
        return mapper.map(input, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputDTO dto, Cidade cidade) {
        cidade.setEstado(new Estado());
        mapper.map(dto, cidade);
    }
}
