package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.in.ProdutoInputDTO;
import com.ibn.algafood.api.model.out.ProdutoOutDTO;
import com.ibn.algafood.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoMapper {

    private final ModelMapper mapper;

    public ProdutoOutDTO domainToDto(Produto produto) {
        return mapper.map(produto, ProdutoOutDTO.class);
    }

    public List<ProdutoOutDTO> domainListToDto(List<Produto> produtos) {
        return produtos.stream().map(p -> this.domainToDto(p)).toList();
    }

    public Produto inputDtoToDomain(ProdutoInputDTO inputDTO) {
        return mapper.map(inputDTO, Produto.class);
    }

    public void copyToDomainObject(ProdutoInputDTO dto, Produto produto) {
        mapper.map(dto, produto);
    }

}
