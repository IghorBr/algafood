package com.ibn.algafood.core.validation;

import com.ibn.algafood.core.validation.validator.FreteGratisDescricaoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FreteGratisDescricaoValidator.class })
public @interface FreteGratisDescricao {

    String message() default "não possui a descrição obrigatória";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String valorField();
    String descricaoField();
    String descricaoObrigatoria();
}
