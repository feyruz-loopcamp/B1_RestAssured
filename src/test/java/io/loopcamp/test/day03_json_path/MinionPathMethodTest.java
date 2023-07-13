package io.loopcamp.test.day03_json_path;



import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionPathMethodTest extends MinionTestBase {

    /**
     * Given accept is json
     * And path param id is 13
     * When I send GET request to /minions/{id}
     * ----------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */


    @DisplayName("GET /minion/{id} with path()")
    @Test
    public void readMinionJsonUsingPathTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/minions/{id}");


        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());


        //response.prettyPrint();
        /**
         * {
         *     "id": 13,
         *     "gender": "Female",
         *     "name": "Jaimie",
         *     "phone": "7842554879"
         * }
         */
        System.out.println("id = " + response.path("id"));
        System.out.println("gender = " + response.path("gender"));
        System.out.println("name = " + response.path("name"));
        System.out.println("phone = " + response.path("phone"));


        assertEquals("13", response.path("id")+"");   // The reason we did concatenation is because the data type in Minion application is not determined.
        assertEquals("Female", response.path("gender"));
        assertEquals("Jaimie", response.path("name"));
        assertEquals("7842554879", response.path("phone"));


    }


    /**
         Given accept is json
         When I send get request to /minions
     --------------------------------------------
         Then status code is 200
         And content type is json
         And I can navigate json array object
     */

    @DisplayName("GET /minions and path()")
    @Test
    public void readMinionJsonArrayUsingPathTest () {

        Response response = given().accept(ContentType.JSON)
                .and().get("/minions");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //response.prettyPrint();
        /*
        [
             {
                "id": 1,
                "gender": "Male",
                "name": "Meade",
                "phone": "3584128232"
            },
            {
                "id": 2,
                "gender": "Male",
                "name": "Nels",
                "phone": "4218971348"
            }
            .
            .
            .
          ]
         */

        // Print the first minion id
        //System.out.println("1st minion id: " + response.path("[0].id"));  // This is doing the same thing as above.
        System.out.println("1st minion id: " + response.path("id[0]"));
        System.out.println("1st minion name: " + response.path("[0].name"));
        System.out.println("1st minion gender: " + response.path("gender[0]"));


        System.out.println();
        // Print the last minion information
        System.out.println("last minion id: " + response.path("id[-1]"));
        //System.out.println("last minion name: " + response.path("[-1].name"));  // When we want to get the last index, this syntax is NOT the CORRECT one to use
        System.out.println("last minion name: " + response.path("name[-1]"));
        System.out.println("last minion gender: " + response.path("gender[-1]"));


        System.out.println();
        //how to get all the id's
        //System.out.println("all minions id: " + response.path("id"));
        List<Integer> allIds = response.path("id");
        System.out.println("all minions id: " + allIds);


        // Get all minions name and print out in this format --- > Hi "name"!
        List <String> allNames = response.path("name");
        for (String eachName: allNames) {
            System.out.println("Hi " + eachName + "!");
        }

        System.out.println();
        // with lambda expression
        allNames.forEach( eachName -> System.out.println("Bye " + eachName + "!"));

    }


}
