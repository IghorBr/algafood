package com.ibn.algafood.api.exception_handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    private final MessageSource messageSource;

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = this.createErrorBuilder(status, ErrorType.RECURSO_NAO_ENCONTRADO, e.getMessage())
                .userMessage(MSG_ERRO_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AlgafoodException.class)
    public ResponseEntity<Object> handleAlgafoodException(AlgafoodException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Error error = this.createErrorBuilder(status, ErrorType.ALGAFOOD_EXCEPTION, e.getMessage())
                .userMessage(MSG_ERRO_USUARIO_FINAL).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error error = this.createErrorBuilder(status, ErrorType.ENTIDADE_EM_USO, e.getMessage())
                .userMessage(MSG_ERRO_USUARIO_FINAL).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e, WebRequest request) {
        var detail = MSG_ERRO_USUARIO_FINAL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = this.createErrorBuilder(status, ErrorType.ERRO_DE_SISTEMA, detail)
                .userMessage(MSG_ERRO_USUARIO_FINAL).build();

        return this.handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        var detail = MSG_ERRO_USUARIO_FINAL;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<Error.Field> fields = e.getConstraintViolations().stream().map(cv -> {
            return Error.Field.builder().name(cv.getPropertyPath().toString())
                    .userMessage("O atributo '" + cv.getPropertyPath().toString() + "' " + cv.getMessage()).build();
        }).toList();

        Error error = this.createErrorBuilder(status, ErrorType.DADOS_INVALIDOS, detail)
                .userMessage(detail)
                .fields(fields)
                .build();

        return this.handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorType problemType = ErrorType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        BindingResult bindingResult = ex.getBindingResult();

        List<Error.Field> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Error.Field.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Error error = this.createErrorBuilder(status, problemType, detail)
                .userMessage(detail)
                .fields(problemObjects)
                .build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
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

        Error error = this.createErrorBuilder(status, ErrorType.JSON_INVALIDO, "O corpo da requisição está inválido. Verifique erro de sintaxe.")
                .userMessage(MSG_ERRO_USUARIO_FINAL).build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            var exception = (MethodArgumentTypeMismatchException) ex;

            var detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s",
                    exception.getName(), exception.getValue(), exception.getRequiredType().getSimpleName());

            Error error = this.createErrorBuilder(status, ErrorType.PARAMETRO_INVALIDO, detail)
                    .userMessage(MSG_ERRO_USUARIO_FINAL).build();

            return this.handleExceptionInternal(ex, error, headers, status, request);
        }
        else {
            return super.handleTypeMismatch(ex, headers, status, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var detail = String.format("O recurso %s que você tentou acessar, é inexistente.", ex.getRequestURL());

        Error error = this.createErrorBuilder(status, ErrorType.RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(MSG_ERRO_USUARIO_FINAL).build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String detail = String.format("A propriedade '%s' não existe na entidade %s", ex.getPropertyName(), ex.getReferringClass().getSimpleName());

        Error error = this.createErrorBuilder(status, ErrorType.JSON_INVALIDO, detail)
                .userMessage(MSG_ERRO_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);

    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String field = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', " +
                "que é de um tipo inválido. Corrija informando um valor compatível com o tipo %s.",
                field, ex.getValue(), ex.getTargetType().getSimpleName() );

        Error error = this.createErrorBuilder(status, ErrorType.JSON_INVALIDO, detail)
                .userMessage(MSG_ERRO_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();

        if (Objects.isNull(body)) {
            body = Error.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .timestamp(OffsetDateTime.now())
                    .userMessage(MSG_ERRO_USUARIO_FINAL)
                    .build();
        }
        else if (body instanceof String) {
            body = Error.builder()
                    .title((String) body)
                    .status(status.value())
                    .timestamp(OffsetDateTime.now())
                    .userMessage(MSG_ERRO_USUARIO_FINAL)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Error.ErrorBuilder createErrorBuilder(HttpStatus status, ErrorType type, String detail) {
        return Error.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }
}
