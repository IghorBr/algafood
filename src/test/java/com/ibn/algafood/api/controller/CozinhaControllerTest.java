package com.ibn.algafood.api.controller;

import com.ibn.algafood.domain.model.Cozinha;
import com.ibn.algafood.domain.service.CozinhaService;
import com.ibn.algafood.util.DatabaseCleaner;
import com.ibn.algafood.util.ResourceUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaControllerTest {

    @LocalServerPort
    private int PORT;
    private final String PATH = "/cozinhas";

    private static int QUANT_COZINHAS_CADASTRADAS = 0;
    private static final int ID_COZINHA_NAO_EXISTENTE = 100;

    private Cozinha cozinhaTeste;

    private @Autowired DatabaseCleaner cleaner;
    private @Autowired CozinhaService cozinhaService;

    @BeforeEach
    public void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = PORT;
        basePath = PATH;

        cleaner.clearTables();
        prepareData();
    }

    @Test
    public void testarCodigoRetornoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(OK.value());
    }
    @Test
    public void testarQuantidadeCozinhas() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", hasSize(CozinhaControllerTest.QUANT_COZINHAS_CADASTRADAS))
        ;
    }

    @Test
    public void testarInsercaoCozinha() {
        String resource = ResourceUtils.getContentFromResource("/json/cozinha_ok_test.json");

        given()
                .body(resource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(CREATED.value());
    }

    @Test
    public void testarStatusECorpoBuscaCozinhaExistente() {
        given()
                .pathParam("id", cozinhaTeste.getId())
                .accept(ContentType.JSON)
        .when()
                .get("/{id}")
        .then()
                .statusCode(OK.value())
                .body("nome", equalTo(cozinhaTeste.getNome()))
        ;
    }

    @Test
    public void testarStatusECorpoBuscaCozinhaNaoExistente() {
        given()
                .pathParam("id", ID_COZINHA_NAO_EXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(NOT_FOUND.value())
        ;
    }



    private void prepareData() {
        Cozinha c1 = new Cozinha();
        c1.setNome("Brasileira");
        cozinhaService.save(c1);

        CozinhaControllerTest.QUANT_COZINHAS_CADASTRADAS++;

        cozinhaTeste = new Cozinha();
        cozinhaTeste.setNome("Americana");
        cozinhaService.save(cozinhaTeste);

        CozinhaControllerTest.QUANT_COZINHAS_CADASTRADAS++;
    }
}
