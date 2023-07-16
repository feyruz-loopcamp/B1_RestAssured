package io.loopcamp.test.day05_b_deserialization;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionToMapTest extends MinionTestBase {

    /**
         Given accept type is application/json
         And Path param id = 10
         When i send GET request to /minions
         ------------------------------------------------
         Then status code is 200
         And content type is json
         And minion data matching:
         id > 10
         name>Lorenza
         gender >Female
         phone >3312820936
     */


    @DisplayName("GET /minions/{id} - to MAP")
    @Test
    public void minionToMapTest() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .and().get("/minions/{id}");

        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json", response.contentType());


        // CONVERTING JSON to MAP --- > DE-SERIALIZATION
        Map <String, Objects> minionMap = response.body().as(Map.class);  // response.as(Map.class); ---> this can do the same thing.
        System.out.println("MinionMap: " + minionMap);

        // how can we get all the keys
        System.out.println("Keys: " + minionMap.keySet());


        /*
             id > 10
             name>Lorenza
             gender >Female
             phone >3312820936
         */

        assertEquals(10, minionMap.get("id"));
        assertEquals("Lorenza", minionMap.get("name"));
        assertEquals("Female", minionMap.get("gender"));
        assertEquals("" + 3312820936l, minionMap.get("phone"));


    }

}
