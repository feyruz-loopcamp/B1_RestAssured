package io.loopcamp.test.day10_jsonschema_validator;

import io.loopcamp.util.MinionTestBase;

import java.io.File;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MinionGetJsonValidationTest extends MinionTestBase {

    /**
         given accept type is json
         and path param id is 15
         when I send GET request to /minions/{id}
         Then status code is 200
         And json payload/body matches SingleMinionSchema.json
     */

    @DisplayName("GET /minions/{id} and json schema validation")
    @Test
    public void singleMinionSchemaValidationTest() {
        /**
         given()
         and()
         and()
         when()
         and()
         then()
         and()
         */

        given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/minions/{id}")
                .then().assertThat().statusCode(is(200))
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File( "src/test/resources/jsonschemas/SingleMinionSchema.json" )))
                .log().all();


    }


    /**
         given accept type is json
         when I send GET request to /minions
         Then status code is 200
         And json payload/body matches AllMinionsSchema.json
     */
    @DisplayName("GET /minions and json schema validation")
    @Test
    public void allMinionsJsonSchemaValidationTest() {
        given().accept(ContentType.JSON)
                .when().get("/minions")
                .then().assertThat().statusCode(is(HttpStatus.SC_OK))
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/AllMinionsSchema.json")))
                .log().all();

    }


    /**
         given accept type is json
         And query params: nameContains "e" and gender "Female"
         when I send GET request to /minions/search
         Then status code is 200
         And json payload/body matches SearchMinionsSchema.json
     */

    @DisplayName("GET /minions/search and json schema validation")
    @Test
    public void searchMinionJsonSchemaValidationTest() {

        given().accept(ContentType.JSON)
                .and().queryParam("nameContains", "e")
                .and().queryParam("gender", "Female")
                .when().get("/minions/search")
                .then().assertThat().statusCode(is(equalTo(HttpStatus.SC_OK)))
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/SearchMinionsSchema.json")
                )).log().all();


    }











}
