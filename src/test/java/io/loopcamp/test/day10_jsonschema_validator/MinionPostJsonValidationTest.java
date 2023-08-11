package io.loopcamp.test.day10_jsonschema_validator;

import io.loopcamp.util.MinionTestBase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.loopcamp.util.MinionRestUtil;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MinionPostJsonValidationTest extends MinionTestBase {



    /**
         given accept is json
         and content type is json
         and body is:
             {
                 "gender": "Male",
                 "name": "TestPost1"
                 "phone": 1234567425
             }
         When I send POST request to /minions
            ----------------
         Then status code is 201
         And body should match MinionPostSchema.json schema
     */

    @DisplayName("POST /minions - with json schema validation")
    @Test
    public void postMinionJsonSchemaValidationTest () {
//        Minion minion = new Minion();
//        minion.setGender("Male");
//        minion.setName("TestPost1");
//        minion.setPhone("1234567425");

        Map<String,Object> map = new HashMap<>();
        map.put("gender", "Male");
        map.put("name", "TestPost1");
        map.put("phone", "1234567425");

        int newMinionId = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(map)
                .when().post("/minions")
                .then().assertThat().statusCode(is(HttpStatus.SC_CREATED))
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/MinionPostSchema.json")))
                .log().all()
                .and().extract().jsonPath().getInt("data.id");  // with the help of this extract method, we are getting the ID of the newly generated Minion


        System.out.println("Minion ID for newly generate one: " + newMinionId);
        MinionRestUtil.deleteMinionById(newMinionId);


    }

}
