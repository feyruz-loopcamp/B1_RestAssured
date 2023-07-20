package io.loopcamp.test.day05_b_deserialization;

import io.loopcamp.pojo.Minion;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionToPojoTest extends MinionTestBase {


    /**
     *
     *      Given accept type is application/json
     *      And Path param id = 10
     *      When i send GET request to /minions
     *      ---------------------------------------
     *      Then status code is 200
     *      And content type is json
     *      And minion data matching:
     *      id > 10
     *      name>Lorenza
     *      gender >Female
     *      phone >3312820936
     *
     */

    @DisplayName("GET /minions/{id} -- POJO object")
    @Test
    public void minionToPojoTest () {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 10)
                .and().get("/minions/{id}");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // DE_SERIALIZATION -- > from json payload to POJO object
        Minion minion = response.as(Minion.class);

        System.out.println("Minions: " + minion);

        System.out.println("Id: " + minion.getId());
        System.out.println("Name: " + minion.getName());
        System.out.println("Gender: " + minion.getGender());
        System.out.println("Phone: " + minion.getPhone());

        assertEquals(10, minion.getId());
        assertEquals("Lorenza", minion.getName());
        assertEquals("Female", minion.getGender());
        assertEquals("3312820936", minion.getPhone());





    }



}
