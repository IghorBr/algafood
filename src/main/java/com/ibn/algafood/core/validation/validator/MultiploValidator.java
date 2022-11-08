package com.ibn.algafood.core.validation.validator;

import com.ibn.algafood.core.validation.Multiplo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Objects;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return false;

        var valor = BigDecimal.valueOf(value.doubleValue());
        var multiplo = BigDecimal.valueOf(numeroMultiplo);

        return valor.remainder(multiplo).compareTo(BigDecimal.ZERO) == 0;
    }
}
