package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.ibn.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.ibn.algafood.domain.model.*;
import com.ibn.algafood.domain.repository.CozinhaRepository;
import com.ibn.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaService cozinhaRepository;
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;
    private final UsuarioService usuarioService;

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public List<Restaurante> findAll(String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }

    public List<Restaurante> consultarPorNome(final String nome, final Long id) {
        return restauranteRepository.consultarPorNome(nome, id);
    }

    public List<Restaurante> find(final String nome, final BigDecimal taxaInicial, final BigDecimal taxaFinal) {
        return restauranteRepository.findCriteriaApi(nome, taxaInicial, taxaFinal);
    }

    public Restaurante findById(final Long id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public Restaurante save(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.findById(cozinhaId);
        Cidade cidade = cidadeService.findById(restaurante.getEndereco().getCidade().getId());

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.ativar();

        // não é necessário fazer isso, mas para deixar explicito
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.inativar();

        // não é necessário fazer isso, mas para deixar explicito
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = this.findById(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = this.findById(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);

        try {
            restaurante.adicionarFormaPagamento(formaPagamento);
            restauranteRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new AlgafoodException(String.format("O restaurante %s já aceita a forma de pagamento %s", restaurante.getNome(), formaPagamento.getDescricao()));
        }
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = this.findById(id);

        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = this.findById(id);

        restaurante.fechar();
    }

    @Transactional
    public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.findById(restauranteId);
        Usuario usuario = usuarioService.findById(usuarioId);

        restaurante.adicionarResponsavel(usuario);
    }

    @Transactional
    public void removerResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.findById(restauranteId);
        Usuario usuario = usuarioService.findById(usuarioId);

        restaurante.removerResponsavel(usuario);
    }

}
