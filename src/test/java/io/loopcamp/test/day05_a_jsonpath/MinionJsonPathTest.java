package io.loopcamp.test.day05_a_jsonpath;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionJsonPathTest extends MinionTestBase {

    /**
         * Given accept is json
         * And path param id is 13
         * When I send get request to /minions/{id}
         * ----------
         * Then status code is 200
         * And content type is json
         * And id value is 13
         * And name is "Jaimie"
         * And gender is "Female"
         * And phone is "7842554879"
     */


    @DisplayName("GET /minion/{id} - jsonPath")
    @Test
    public void getMinionJsonPathTest() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 13)
                .when().get("/minions/{id}");


        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals("application/json", response.getHeader("content-type"));


        /*
            {
                "id": 13,
                "gender": "Female",
                "name": "Jaimie",
                "phone": "7842554879"
            }
         */

        // First, I need to parse and assign then it to JsonPath
        JsonPath jsonPath = response.jsonPath();
        System.out.println("id=" + jsonPath.getInt("id"));
        System.out.println("gender=" + jsonPath.getString("gender"));
        System.out.println("name=" + jsonPath.getString("name"));
        System.out.println("phone=" + jsonPath.getString("phone"));


        assertEquals(13, jsonPath.getInt("id"));
        assertEquals("Female", jsonPath.getString("gender"));
        assertEquals("Jaimie", jsonPath.getString("name"));
        assertEquals("7842554879", jsonPath.getString("phone"));








    }






}









