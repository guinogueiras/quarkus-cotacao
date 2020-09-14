package br.com.cotacao.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CotacaoResourceTest {

    @Test
    public void testCotacaoEmpty() {
        given()
          .when().get("/cotacao/02-29-2020")
          .then()
             .statusCode(200)
             .body(is("{\"value\":[]}"))
             ;
    }

    @Test
    public void testCotacaoInvalidDate() {
        given()
          .when().get("/cotacao/02-30-2020")
          .then()
             .statusCode(400)
             .body(is("Data 02-30-2020 inválida."))
             ;
    }

    @Test
    public void testCotacaoResponse() {
        given()
            .when().get("/cotacao/02-28-2020")
            .then()
            .statusCode(200)
            .assertThat()
            .body("value[0].compraCotacao", notNullValue())
            .body("value[0].vendaCotacao", notNullValue())
            .body("value[0].dataCotacao", is("2020-02-28"))
            .body("value[0].dataHoraCotacao", notNullValue());
    }

}