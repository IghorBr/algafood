package com.ibn.algafood.api.assembler;

import com.ibn.algafood.api.model.in.CozinhaInputDTO;
import com.ibn.algafood.api.model.out.CozinhaOutDTO;
import com.ibn.algafood.domain.model.Cozinha;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CozinhaDTOAssembler {

    private final ModelMapper mapper;

    public CozinhaOutDTO domainToDto(Cozinha c) {
        return mapper.map(c, CozinhaOutDTO.class);
    }

    public List<CozinhaOutDTO> domainListToDto(List<Cozinha> cozinhas) {
        return cozinhas.stream().map(c -> this.domainToDto(c)).toList();
    }

    public Cozinha inputDtoToDomain(CozinhaInputDTO input) {
        return mapper.map(input, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputDTO dto, Cozinha cozinha) {
        mapper.map(dto, cozinha);
    }
}
