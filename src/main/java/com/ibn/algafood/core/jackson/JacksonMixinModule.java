package com.ibn.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ibn.algafood.api.model.mixin.CidadeMixin;
import com.ibn.algafood.api.model.mixin.CozinhaMixin;
import com.ibn.algafood.api.model.mixin.RestauranteMixin;
import com.ibn.algafood.domain.model.Cidade;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
