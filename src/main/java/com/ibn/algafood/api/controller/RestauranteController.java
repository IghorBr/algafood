package com.ibn.algafood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibn.algafood.api.assembler.RestauranteDTOAssembler;
import com.ibn.algafood.api.model.in.CozinhaInputDTO;
import com.ibn.algafood.api.model.in.RestauranteInputDTO;
import com.ibn.algafood.api.model.out.RestauranteOutDTO;
import com.ibn.algafood.core.validation.Groups;
import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
@Slf4j
public class RestauranteController {

    private final RestauranteService restauranteService;
    private final RestauranteDTOAssembler restauranteAssembler;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator;

    @PostConstruct
    private void postConstruct() {
        this.validator = factory.getValidator();
    }

    @GetMapping
    public ResponseEntity<List<RestauranteOutDTO>> findAll() {
        List<Restaurante> restaurantes = restauranteService.findAll();

        restaurantes.forEach(r -> log.info("A cozinha do restaurante " + r.getNome() + " é " + r.getCozinha().getNome()));
        List<RestauranteOutDTO> dtos = restaurantes.stream().map(r -> restauranteAssembler.domainToOutDto(r)).sorted((a, b) -> a.getId().compareTo(b.getId())).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteOutDTO> findById(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);

        return ResponseEntity.ok(restauranteAssembler.domainToOutDto(restaurante));
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<RestauranteOutDTO>> findByName(@RequestParam("nome") String nome, @RequestParam("id") Long id) {
        List<Restaurante> restaurantes = restauranteService.consultarPorNome(nome, id);

        List<RestauranteOutDTO> dtos = restaurantes.stream().map(r -> restauranteAssembler.domainToOutDto(r)).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/find")
    public ResponseEntity<List<RestauranteOutDTO>> find(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "taxaInicial", required = false) BigDecimal taxaInicial,
            @RequestParam(value = "taxaFinal", required = false) BigDecimal taxaFinal
    ) {
        List<Restaurante> restaurantes = restauranteService.find(nome, taxaInicial, taxaFinal);

        List<RestauranteOutDTO> dtos = restaurantes.stream().map(r -> restauranteAssembler.domainToOutDto(r)).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/gratuito")
    public ResponseEntity<List<RestauranteOutDTO>> findSpec(
            @RequestParam(value = "nome", required = false) String nome
    ) {
        List<Restaurante> restaurantes = restauranteService.findAll(nome);

        List<RestauranteOutDTO> dtos = restaurantes.stream().map(r -> restauranteAssembler.domainToOutDto(r)).toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<RestauranteOutDTO> save(@RequestBody @Validated(Groups.CadastroRestaurante.class) RestauranteInputDTO restauranteInput) {
        try {
            Restaurante restaurante = restauranteAssembler.inputDtoToDomain(restauranteInput);

            restaurante = restauranteService.save(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restauranteAssembler.domainToOutDto(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteOutDTO> update(@PathVariable Long restauranteId,
                                                    @RequestBody @Validated(Groups.CadastroRestaurante.class) RestauranteInputDTO restauranteInputDTO) {
        Restaurante restauranteAtual = restauranteService.findById(restauranteId);

        Restaurante restaurante = restauranteAssembler.inputDtoToDomain(restauranteInputDTO);

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "cozinha", "formasPagamento", "endereco", "dataCadastro", "dataAtualizacao");

        try {
            restauranteAtual = restauranteService.save(restauranteAtual);
            return ResponseEntity.ok(restauranteAssembler.domainToOutDto(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestauranteOutDTO> partialUpdate(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        try {
            Restaurante restaurante = retornaRestaurantePreenchido(id, fields, request);

            validate(restaurante, "restaurante");

            CozinhaInputDTO cozinhaInput = new CozinhaInputDTO();
            cozinhaInput.setId(restaurante.getCozinha().getId());

            RestauranteInputDTO restauranteInput = new RestauranteInputDTO();
            restauranteInput.setNome(restaurante.getNome());
            restauranteInput.setTaxaFrete(restaurante.getTaxaFrete());
            restauranteInput.setCozinha(cozinhaInput);

            return this.update(id, restauranteInput);
        } catch (IllegalAccessException | IllegalArgumentException e ) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    private void validate(Restaurante restaurante, String objectName) {
        Set<ConstraintViolation<Restaurante>> violations = validator.validate(restaurante, Groups.CadastroRestaurante.class);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    private Restaurante retornaRestaurantePreenchido(final Long id, Map<String, Object> fields, HttpServletRequest request) throws IllegalAccessException {
        Restaurante restaurante = restauranteService.findById(id);
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante novoRestaurante = objectMapper.convertValue(fields, Restaurante.class);

            for (String key : fields.keySet()) {
                Field field = ReflectionUtils.findField(Restaurante.class, key);
                field.setAccessible(true);

                Object obj = field.get(novoRestaurante);
                ReflectionUtils.setField(field, restaurante, obj);
            }

            return restaurante;
        } catch (IllegalArgumentException | NullPointerException  e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }
}
