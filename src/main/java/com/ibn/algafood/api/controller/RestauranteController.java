package com.ibn.algafood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
@Slf4j
public class RestauranteController {

    private final RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> findAll() {
        List<Restaurante> restaurantes = restauranteService.findAll();

        restaurantes.forEach(r -> log.info("A cozinha do restaurante " + r.getNome() + " é " + r.getCozinha().getNome()));

        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> findById(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return ResponseEntity.ok(restaurante);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Restaurante>> findByName(@RequestParam("nome") String nome, @RequestParam("id") Long id) {
        return ResponseEntity.ok(restauranteService.consultarPorNome(nome, id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Restaurante>> find(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "taxaInicial", required = false) BigDecimal taxaInicial,
            @RequestParam(value = "taxaFinal", required = false) BigDecimal taxaFinal
    ) {
        return ResponseEntity.ok(restauranteService.find(nome, taxaInicial, taxaFinal));
    }

    @GetMapping("/gratuito")
    public ResponseEntity<List<Restaurante>> findSpec(
            @RequestParam(value = "nome", required = false) String nome
    ) {
        return ResponseEntity.ok(restauranteService.findAll(nome));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteService.save(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> update(@PathVariable Long restauranteId,
                                       @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = restauranteService.findById(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "cozinha", "formasPagamento", "endereco", "dataCadastro", "dataAtualizacao");

        try {
            restauranteAtual = restauranteService.save(restauranteAtual);
            return ResponseEntity.ok(restauranteAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new AlgafoodException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        try {
            Restaurante restaurante = retornaRestaurantePreenchido(id, fields);
            return this.update(id, restaurante);
        } catch (IllegalAccessException | IllegalArgumentException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Restaurante retornaRestaurantePreenchido(final Long id, Map<String, Object> fields) throws IllegalAccessException {
        Restaurante restaurante = restauranteService.findById(id);

        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante novoRestaurante = objectMapper.convertValue(fields, Restaurante.class);

        for (String key : fields.keySet()) {
            Field field = ReflectionUtils.findField(Restaurante.class, key);
            field.setAccessible(true);

            Object obj = field.get(novoRestaurante);
            ReflectionUtils.setField(field, restaurante, obj);
        }

        return restaurante;
    }
}
