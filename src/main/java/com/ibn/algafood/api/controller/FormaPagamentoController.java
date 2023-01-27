package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.FormaPagamentoMapper;
import com.ibn.algafood.api.model.in.FormaPagamentoInputDTO;
import com.ibn.algafood.api.model.out.FormaPagamentoOutDTO;
import com.ibn.algafood.core.validation.Groups;
import com.ibn.algafood.domain.model.FormaPagamento;
import com.ibn.algafood.domain.service.FormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
@RequiredArgsConstructor
public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;
    private final FormaPagamentoMapper formaPagamentoMapper;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutDTO>> findAll(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getDataUltimaAtualizacao();

        if (Objects.nonNull(dataUltimaAtualizacao)) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoMapper.domainListToDto(formaPagamentoService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutDTO> findById(@PathVariable("id") Long id, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getDataUltimaAtualizacao(id);

        if (Objects.nonNull(dataUltimaAtualizacao)) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(20, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoMapper.domainToDto(formaPagamentoService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoOutDTO> save(@RequestBody @Validated(Groups.CadastroFormaPagamento.class) FormaPagamentoInputDTO inputDTO) {
        FormaPagamento fp = formaPagamentoService.save(
                formaPagamentoMapper.dtoInputToDomain(inputDTO)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoMapper.domainToDto(fp));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutDTO> updateById(@PathVariable("id") Long id,
                                                           @RequestBody @Validated(Groups.CadastroFormaPagamento.class) FormaPagamentoInputDTO inputDTO) {
        FormaPagamento formaPagamento = formaPagamentoService.findById(id);
        formaPagamentoMapper.copyToDomainObject(inputDTO, formaPagamento);

        formaPagamento = formaPagamentoService.save(formaPagamento);
        return ResponseEntity.ok(formaPagamentoMapper.domainToDto(formaPagamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        formaPagamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
