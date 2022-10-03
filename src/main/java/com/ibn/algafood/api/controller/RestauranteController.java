package com.ibn.algafood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.model.Restaurante;
import com.ibn.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
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

import static com.ibn.algafood.infrastructure.repository.spec.RestaurantesSpecBuilder.comFreteGratis;
import static com.ibn.algafood.infrastructure.repository.spec.RestaurantesSpecBuilder.nomeSemelhante;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> findAll() {
        return ResponseEntity.ok(restauranteService.findAll());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> findById(@PathVariable Long restauranteId) {
        Optional<Restaurante> restaurante = restauranteService.findById(restauranteId);

        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }

        return ResponseEntity.notFound().build();
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
        return ResponseEntity.ok(restauranteService.findAll(comFreteGratis().and(nomeSemelhante(nome))));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteService.save(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> update(@PathVariable Long restauranteId,
                                       @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = restauranteService
                    .findById(restauranteId).orElse(null);

            if (restauranteAtual != null) {
                BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "cozinha");

                restauranteAtual = restauranteService.save(restauranteAtual);
                return ResponseEntity.ok(restauranteAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        try {
            Restaurante restaurante = retornaRestaurantePreenchido(id, fields);
            restaurante = restauranteService.save(restaurante);

            return ResponseEntity.ok(restaurante);
        } catch (IllegalAccessException | IllegalArgumentException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Restaurante retornaRestaurantePreenchido(final Long id, Map<String, Object> fields) throws IllegalAccessException {
        Restaurante restaurante = restauranteService.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Restaurante de código %s não encontrado.", id)));

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
