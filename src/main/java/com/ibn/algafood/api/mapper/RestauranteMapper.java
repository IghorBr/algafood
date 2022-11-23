package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.in.RestauranteInputDTO;
import com.ibn.algafood.api.model.out.RestauranteOutDTO;
import com.ibn.algafood.domain.model.Cidade;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RestauranteMapper {

    private final ModelMapper mapper;

    public RestauranteOutDTO domainToOutDto(Restaurante r) {
        return mapper.map(r, RestauranteOutDTO.class);
    }

    public Restaurante inputDtoToDomain(RestauranteInputDTO restaurante) {
        return mapper.map(restaurante, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInputDTO restauranteInput, Restaurante restaurante) {
        // To avoid
        // identifier of an instance of com.ibn.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if (Objects.nonNull(restaurante.getEndereco()))
            restaurante.getEndereco().setCidade(new Cidade());

        mapper.map(restauranteInput, restaurante);
    }
}
