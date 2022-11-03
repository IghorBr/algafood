package com.ibn.algafood.api.exception_handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = this.createErrorBuilder(status, ErrorType.ENTIDADE_NAO_ENCONTRADA, e.getMessage())
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AlgafoodException.class)
    public ResponseEntity<?> handleAlgafoodException(AlgafoodException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Error error = this.createErrorBuilder(status, ErrorType.ALGAFOOD_EXCEPTION, e.getMessage()).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error error = this.createErrorBuilder(status, ErrorType.ENTIDADE_EM_USO, e.getMessage()).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }
        if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        Error error = this.createErrorBuilder(status, ErrorType.JSON_INVALIDO, "O corpo da requisição está inválido. Verifique erro de sintaxe.").build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String detail = String.format("A propriedade '%s' não existe na entidade %s", ex.getPropertyName(), ex.getReferringClass().getSimpleName());

        Error error = this.createErrorBuilder(status, ErrorType.JSON_INVALIDO, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);

    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String field = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', " +
                "que é de um tipo inválido. Corrija informando um valor compatível com o tipo %s.",
                field, ex.getValue(), ex.getTargetType().getSimpleName() );

        Error error = this.createErrorBuilder(status, ErrorType.JSON_INVALIDO, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (Objects.isNull(body)) {
            body = Error.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        }
        else if (body instanceof String) {
            body = Error.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Error.ErrorBuilder createErrorBuilder(HttpStatus status, ErrorType type, String detail) {
        return Error.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail);
    }
}
