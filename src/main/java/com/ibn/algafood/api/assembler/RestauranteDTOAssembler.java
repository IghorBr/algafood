package com.ibn.algafood.api.assembler;

import com.ibn.algafood.api.model.in.RestauranteInputDTO;
import com.ibn.algafood.api.model.out.CozinhaOutDTO;
import com.ibn.algafood.api.model.out.RestauranteOutDTO;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOAssembler {

    public RestauranteOutDTO domainToOutDto(Restaurante r) {
        CozinhaOutDTO c = CozinhaOutDTO.builder()
                .id(r.getCozinha().getId())
                .nome(r.getCozinha().getNome())
                .build();

        return RestauranteOutDTO.builder()
                .id(r.getId())
                .nome(r.getNome())
                .taxaFrete(r.getTaxaFrete())
                .cozinha(c)
                .build();
    }

    public Restaurante inputDtoToDomain(RestauranteInputDTO restaurante) {
        Cozinha c = new Cozinha();
        c.setId(restaurante.getCozinha().getId());

        Restaurante r = new Restaurante();
        r.setNome(restaurante.getNome());
        r.setTaxaFrete(restaurante.getTaxaFrete());
        r.setCozinha(c);

        return r;
    }
}
