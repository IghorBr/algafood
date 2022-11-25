package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.ProdutoMapper;
import com.ibn.algafood.api.model.in.ProdutoInputDTO;
import com.ibn.algafood.api.model.out.ProdutoOutDTO;
import com.ibn.algafood.domain.model.Produto;
import com.ibn.algafood.domain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@RequiredArgsConstructor
public class RestauranteProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    @GetMapping
    public ResponseEntity<List<ProdutoOutDTO>> findAll(@PathVariable("restauranteId") Long id) {
        List<Produto> produtos = produtoService.findAll(id);

        return ResponseEntity.ok(produtoMapper.domainListToDto(produtos));
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutDTO> findById(@PathVariable("restauranteId") Long restauranteId,
                                                  @PathVariable("produtoId") Long produtoId) {
        Produto produto = produtoService.findById(produtoId, restauranteId);

        return ResponseEntity.ok(produtoMapper.domainToDto(produto));
    }

    @PostMapping
    public ResponseEntity<ProdutoOutDTO> save(@PathVariable("restauranteId") Long id,
                                              @RequestBody @Valid ProdutoInputDTO inputDTO) {
        Produto produto = produtoMapper.inputDtoToDomain(inputDTO);
        produto = produtoService.save(id, produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoMapper.domainToDto(produto));

    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutDTO> updateById(@PathVariable("restauranteId") Long restauranteId,
                                                    @PathVariable("produtoId") Long produtoId,
                                                    @RequestBody @Valid ProdutoInputDTO inputDTO) {
        Produto produto = produtoService.findById(produtoId, restauranteId);
        produtoMapper.copyToDomainObject(inputDTO, produto);

        produto = produtoService.save(restauranteId, produto);

        return ResponseEntity.ok().body(produtoMapper.domainToDto(produto));
    }
}
