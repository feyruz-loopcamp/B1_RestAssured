package io.loopcamp.test.day11_a_authorization;

import io.loopcamp.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class MinionBasicAuthTest extends MinionSecureTestBase {


    /**
         given accept type is json
         and basic auth credentials are loopacademy , loopacademy
         When user sends GET request to /minions
                -------
         Then status code is 200
         And content type is json
         And print response
     */
    @DisplayName("GET request to /minions with basic auth")
    @Test
    public void getMinionWithAuthTest () {

        given().accept(ContentType.JSON)
                .and().auth().basic("loopacademy", "loopacademy")
                .when().get("/minions")
                .then().assertThat().statusCode(is(200))
                .and().contentType(ContentType.JSON)
                .log().all();

    }


    /**
         given accept type is json
         When user sends GET request to /minions
         -------
         Then status code is 401
         And content type is json
         And "error" is "Unauthorized"
         And print response
     */

    // Negative Test Case -- > Rainy Day
    @Test
    public void getAllMinionsUnauthorizedTest() {

        given().accept(ContentType.JSON)
                .when().get("/minions")
                .then().statusCode(is(401))
                .and().contentType(ContentType.JSON)
                .and().body("error", equalTo("Unauthorized"))
                .log().all();


    }



}
