package com.ibn.algafood.core.validation.validator;

import com.ibn.algafood.core.validation.FreteGratisDescricao;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class FreteGratisDescricaoValidator implements ConstraintValidator<FreteGratisDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(FreteGratisDescricao constraintAnnotation) {
        descricaoField = constraintAnnotation.descricaoField();
        valorField = constraintAnnotation.valorField();
        descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Field fieldValor = ReflectionUtils.findField(value.getClass(), valorField);
        Field fieldDescricao = ReflectionUtils.findField(value.getClass(), descricaoField);

        if (isNull(fieldValor))
            throw new ValidationException("O campo " + valorField + " não existe na entidade " + value.getClass().getSimpleName().toUpperCase());
        if (isNull(fieldDescricao))
            throw new ValidationException("O campo " + descricaoField + " não existe na entidade " + value.getClass().getSimpleName().toUpperCase());

        fieldValor.setAccessible(true);
        fieldDescricao.setAccessible(true);

        BigDecimal valorFrete;
        String descricao;

        try {
            valorFrete = (BigDecimal) fieldValor.get(value);
            descricao = (String) fieldDescricao.get(value);
        } catch (IllegalAccessException e) {
            throw new ValidationException(e);
        }

        if (nonNull(valorFrete) && valorFrete.compareTo(BigDecimal.ZERO) == 0 && nonNull(descricao))
            return descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());

        return true;
    }
}
