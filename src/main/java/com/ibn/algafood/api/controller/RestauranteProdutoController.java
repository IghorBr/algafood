package com.ibn.algafood.api.controller;

import com.ibn.algafood.api.mapper.FotoProdutoMapper;
import com.ibn.algafood.api.mapper.ProdutoMapper;
import com.ibn.algafood.api.model.in.FotoProdutoInputDTO;
import com.ibn.algafood.api.model.in.ProdutoInputDTO;
import com.ibn.algafood.api.model.out.FotoProdutoOutDTO;
import com.ibn.algafood.api.model.out.ProdutoOutDTO;
import com.ibn.algafood.core.security.CheckSecurity;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.model.FotoProduto;
import com.ibn.algafood.domain.model.Produto;
import com.ibn.algafood.domain.service.FotoStorageService;
import com.ibn.algafood.domain.service.ProdutoService;
import com.ibn.algafood.infrastructure.service.storage.LocalStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;
    private final FotoProdutoMapper fotoProdutoMapper;
    private final FotoStorageService storageService;

    public RestauranteProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper, FotoProdutoMapper fotoProdutoMapper, LocalStorageService storageService) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
        this.fotoProdutoMapper = fotoProdutoMapper;
        this.storageService = storageService;
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public ResponseEntity<List<ProdutoOutDTO>> findAll(@PathVariable("restauranteId") Long id,
                                                       @RequestParam(required = false) boolean findInativos) {
        List<Produto> produtos;

        if (findInativos)
            produtos = produtoService.findAll(id);
        else
            produtos = produtoService.findAtivos(id);

        return ResponseEntity.ok(produtoMapper.domainListToDto(produtos));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutDTO> findById(@PathVariable("restauranteId") Long restauranteId,
                                                  @PathVariable("produtoId") Long produtoId) {
        Produto produto = produtoService.findById(produtoId, restauranteId);

        return ResponseEntity.ok(produtoMapper.domainToDto(produto));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping
    public ResponseEntity<ProdutoOutDTO> save(@PathVariable("restauranteId") Long id,
                                              @RequestBody @Valid ProdutoInputDTO inputDTO) {
        Produto produto = produtoMapper.inputDtoToDomain(inputDTO);
        produto = produtoService.save(id, produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoMapper.domainToDto(produto));

    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutDTO> updateById(@PathVariable("restauranteId") Long restauranteId,
                                                    @PathVariable("produtoId") Long produtoId,
                                                    @RequestBody @Valid ProdutoInputDTO inputDTO) {
        Produto produto = produtoService.findById(produtoId, restauranteId);
        produtoMapper.copyToDomainObject(inputDTO, produto);

        produto = produtoService.save(restauranteId, produto);

        return ResponseEntity.ok().body(produtoMapper.domainToDto(produto));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(value = "/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotoProdutoOutDTO> getFotoInfo(@PathVariable("restauranteId") Long restauranteId,
                                                     @PathVariable("produtoId") Long produtoId) {
        FotoProduto foto = produtoService.getFoto(restauranteId, produtoId);
        FotoProdutoOutDTO dto = fotoProdutoMapper.domainToDto(foto);

        return ResponseEntity.ok(dto);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(value = "/{produtoId}/foto")
    public ResponseEntity<InputStreamResource> getFoto(@PathVariable("restauranteId") Long restauranteId,
                                                       @PathVariable("produtoId") Long produtoId,
                                                       @RequestHeader(name = "accept") String accept) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto foto = produtoService.getFoto(restauranteId, produtoId);

            MediaType mediaType = MediaType.parseMediaType(foto.getContentType());
            List<MediaType> mediaTypesList = MediaType.parseMediaTypes(accept);
            verificarMediaType(mediaType, mediaTypesList);

            InputStream inputStream = storageService.recuperar(foto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(value = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoOutDTO> updatePicture(
            @PathVariable("restauranteId") Long restauranteId,
            @PathVariable("produtoId") Long produtoId,
            @Valid FotoProdutoInputDTO dto) throws IOException {

        Produto produto = produtoService.findById(produtoId, restauranteId);
        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(dto.getDescricao());
        foto.setContentType(dto.getArquivo().getContentType());
        foto.setTamanho(dto.getArquivo().getSize());
        foto.setNomeArquivo(dto.getArquivo().getOriginalFilename());

        foto = produtoService.salvarFoto(foto, dto.getArquivo().getInputStream());

        FotoProdutoOutDTO fotoProdutoDTO = fotoProdutoMapper.domainToDto(foto);

        return ResponseEntity.ok(fotoProdutoDTO);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{produtoId}/foto")
    public ResponseEntity<Void> deletarFoto(@PathVariable("restauranteId") Long restauranteId,
                                            @PathVariable("produtoId") Long produtoId) {
        produtoService.excluirFoto(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }

    private void verificarMediaType(MediaType mediaType, List<MediaType> mediaTypesList) throws HttpMediaTypeNotAcceptableException {
        boolean isCompativel = mediaTypesList.stream().anyMatch(m -> m.isCompatibleWith(mediaType));

        if (!isCompativel)
            throw new HttpMediaTypeNotAcceptableException(mediaTypesList);
    }
}
