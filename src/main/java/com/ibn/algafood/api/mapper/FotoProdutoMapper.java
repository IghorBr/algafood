package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.out.FotoProdutoOutDTO;
import com.ibn.algafood.domain.model.FotoProduto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FotoProdutoMapper {

    private final ModelMapper mapper;

    public FotoProdutoOutDTO domainToDto(FotoProduto fotoProduto) {
        return mapper.map(fotoProduto, FotoProdutoOutDTO.class);
    }
}
