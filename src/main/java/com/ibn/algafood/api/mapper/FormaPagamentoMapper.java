package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.in.FormaPagamentoInputDTO;
import com.ibn.algafood.api.model.out.FormaPagamentoOutDTO;
import com.ibn.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FormaPagamentoMapper {

    private final ModelMapper mapper;

    public FormaPagamentoOutDTO domainToDto(FormaPagamento formaPagamento) {
        return mapper.map(formaPagamento, FormaPagamentoOutDTO.class);
    }

    public List<FormaPagamentoOutDTO> domainListToDto(List<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream().map(fp -> this.domainToDto(fp)).toList();
    }

    public FormaPagamento dtoInputToDomain(FormaPagamentoInputDTO inputDTO) {
        return mapper.map(inputDTO, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInputDTO dto, FormaPagamento formaPagamento) {
        mapper.map(dto, formaPagamento);
    }
}
