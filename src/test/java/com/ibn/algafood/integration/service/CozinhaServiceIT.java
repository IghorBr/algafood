package com.ibn.algafood.integration.service;

import com.ibn.algafood.core.validation.Groups;
import com.ibn.algafood.domain.exception.EntidadeEmUsoException;
import com.ibn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.service.CozinhaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CozinhaServiceIT {

    private @Autowired CozinhaService cozinhaService;
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testarCadastroCozinhaSucesso() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        novaCozinha = cozinhaService.save(novaCozinha);

        assertNotNull(novaCozinha);
        assertNotNull(novaCozinha.getId());
    }

    @Test
    public void testarCadastroCozinha_SemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        Set<ConstraintViolation<Cozinha>> exception = validator.validate(novaCozinha, Groups.CadastroCozinha.class);

        assertNotNull(exception);
        assertEquals(exception.size(), 1);
    }

    @Test
    public void testarExclusaoCozinha_EmUso() {
        EntidadeEmUsoException exception = assertThrows(EntidadeEmUsoException.class, () -> cozinhaService.deleteById(1L));

        assertNotNull(exception);
    }

    @Test
    public void testarExclusaoCozinha_NaoInexistente() {
        EntidadeNaoEncontradaException exception = assertThrows(EntidadeNaoEncontradaException.class, () -> cozinhaService.deleteById(100L));

        assertNotNull(exception);
    }
}
