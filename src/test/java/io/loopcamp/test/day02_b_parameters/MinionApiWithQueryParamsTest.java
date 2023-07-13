package io.loopcamp.test.day02_b_parameters;

import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionApiWithQueryParamsTest {

    String url = "http://44.204.229.13:8000/api/minions/search";
    /**
     *         Given accept type is Json
     *         And query parameter values are:
     *          Gender | Female
     *          nameContains | e
     *         When user sends GET request to /api/minions/search
     *         ------------------------------------------------------
     *         Then response status code should be 200
     *         And response content-type: application/json
     *         And "Female" should be in response body
     *         And "Janette" should be in response body
     */


    @DisplayName("GET /api/minions/search with query params")
    @Test
    public void searchForMinionTest () {

        Map<String, String> qp = new HashMap<>();
        qp.put("gender", "Female");
        qp.put("nameContains", "e");


        Response response = given().log().all().accept(ContentType.JSON)
//                .and().queryParam("gender", "Female")
//                .and().queryParam("nameContains", "e")
                .and().queryParams("gender", "Female", "nameContains", "e")   // You can pass multiple query params in the parenthesis
//                .and().queryParams(qp)  // Or you can do with Map
                .when().get(url);

        //verify status code is 2000
        System.out.println("Status Code: " + response.statusCode());
        assertEquals(200, response.statusCode());


        // verify response header
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals("application/json", response.contentType());

        //response.prettyPrint();

        // verify the followings
        // And "Female" should be in response body
        // And "Janette" should be in response body
        assertTrue(response.asString().contains("Female"));
        assertTrue(response.asString().contains("Janette"));


    }
}















