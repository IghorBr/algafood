package com.ibn.algafood.api.exception_handler;

import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

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
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (Objects.nonNull(body)) {
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
