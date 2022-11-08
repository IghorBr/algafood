package com.ibn.algafood.domain.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ValidacaoException extends AlgafoodException {

    private BindingResult bindingResult;

    public ValidacaoException(String message) {
        super(message);
    }

    public ValidacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidacaoException(String message, BindingResult bindingResult) {
        this(message);
        this.bindingResult = bindingResult;
    }
}
