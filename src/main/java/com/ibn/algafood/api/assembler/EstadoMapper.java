package com.ibn.algafood.api.assembler;

import com.ibn.algafood.api.model.in.EstadoInputDTO;
import com.ibn.algafood.api.model.out.EstadoOutDTO;
import com.ibn.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstadoMapper {

    private final ModelMapper mapper;

    public EstadoOutDTO domainToDto(Estado estado) {
        return mapper.map(estado, EstadoOutDTO.class);
    }

    public List<EstadoOutDTO> domainListToDto(List<Estado> estados) {
        return estados.stream().map(e -> this.domainToDto(e)).toList();
    }

    public Estado inputDtoToDomain(EstadoInputDTO inputDTO) {
        return mapper.map(inputDTO, Estado.class);
    }

    public void copyToDomainObject(EstadoInputDTO dto, Estado estado) {
        mapper.map(dto, estado);
    }
}
