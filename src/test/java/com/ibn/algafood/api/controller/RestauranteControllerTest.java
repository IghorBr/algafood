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

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestauranteControllerTest {

    @LocalServerPort
    private int PORT;
    private final String PATH = "/restaurantes";

    private @Autowired DatabaseCleaner cleaner;
    private @Autowired CozinhaService cozinhaService;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = PORT;
        basePath = PATH;

        cleaner.clearTables();
        prepareData();
    }

    @Test
    public void testarConsultaRestaurantes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(OK.value());
    }

    @Test
    public void testarCadastroRestaurante() {
        String resource = ResourceUtils.getContentFromResource("/json/restaurante_ok_test.json");

        given()
                .accept(ContentType.JSON)
                .body(resource)
                .contentType(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(CREATED.value())
                ;
    }

    private void prepareData() {
        Cozinha c1 = new Cozinha();
        c1.setNome("Brasileira");
        cozinhaService.save(c1);
    }


}
