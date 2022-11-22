package com.ibn.algafood.core.modelmapper;

import com.ibn.algafood.api.model.out.RestauranteOutDTO;
import com.ibn.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var mapper =  new ModelMapper();

        mapper.createTypeMap(Restaurante.class, RestauranteOutDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteOutDTO::setTaxaFrete)
                ;

        return mapper;
    }
}
