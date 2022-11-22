package com.ibn.algafood.api.assembler;

import com.ibn.algafood.api.model.out.EstadoOutDTO;
import com.ibn.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstadoDTOAssembler {

    private final ModelMapper mapper;

    public EstadoOutDTO domainToDto(Estado estado) {
        return mapper.map(estado, EstadoOutDTO.class);
    }

    public List<EstadoOutDTO> domainListToDto(List<Estado> estados) {
        return estados.stream().map(e -> this.domainToDto(e)).toList();
    }
}
