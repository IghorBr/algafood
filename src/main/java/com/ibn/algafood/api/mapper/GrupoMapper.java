package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.in.GrupoInputDTO;
import com.ibn.algafood.api.model.out.GrupoOutDTO;
import com.ibn.algafood.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrupoMapper {

    private final ModelMapper mapper;

    public GrupoOutDTO domainToDto(Grupo grupo) {
        return mapper.map(grupo, GrupoOutDTO.class);
    }

    public List<GrupoOutDTO> domainListToDto(List<Grupo> grupos) {
        return grupos.stream().map(g -> this.domainToDto(g)).toList();
    }

    public Grupo inputDtoToDomain(GrupoInputDTO dto) {
        return mapper.map(dto, Grupo.class);
    }

    public void copyToDomainObject(GrupoInputDTO dto, Grupo grupo) {
        mapper.map(dto, grupo);
    }
}
