package io.loopcamp.test.day07_a_hamcrest;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

public class MinionHamcrestTest extends MinionTestBase {


    /**
         given accept type is json
         and path id is 24
         When i send get request to /minions/{id}
            ---------------
         then status code is 200
         and content type is application/json
         and id" is 24,
         "name" is "Julio",
         "gender" is "Male",
         "phone" is 9393139934
     */

    @DisplayName("GET /minions/{id} -- > hamcrest matchers")
    @Test
    public void singleMinionTest() {

        given().accept(ContentType.JSON)
                .and().pathParam("id", 24)
                .when().get("/minions/{id}")   // In normal cases, we would store this into Response response and the use one of the ways (path(), jsonPath()) to do my validation
                .then().statusCode(is(200))
                .and().contentType(ContentType.JSON)
                .and().assertThat().body("id", is(24),
                        "name", is(equalTo("Julio")),
                        "gender", is("Male"),
                        "phone", is("9393139934"));

        /**
            hamcrest are good work readability of the code but if I need to extract one value from the json payload, for example name=Julio, and then
            I want to write a query for example: SELECT * FROM employees WHERE first_name = "Julio";
         */

    }


    /**
         Given accept type is json
         And query param nameContains value is "e"
         And query param gender value is "Female"
         When I send get request to /minions/search
            --------------
         Then status code is 200
         And content type is Json
         And header date contains 2023
         And json response data is as expected
             totalElement is 33
             has ids: 94, 98,91, 81
             has names: Jocelin, Georgianne, Catie, Marylee,Elita
             every gender is Female
             every name contains e
     */

    @DisplayName("GET /minions/search --- hamcrest assertion")
    @Test
    public void minionSearchTest () {

        given().accept(ContentType.JSON)
                .and().queryParam("nameContains", "e")
                .and().queryParam("gender", "Female")
                .when().get("/minions/search")                      // We can store upto this part into Response response
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().header("Date", containsString("2023"))  //If you are watching from future make sure you update the year that you are in currently.
                .and().header("Date", containsString(LocalDate.now().getYear()+""))   // LocalDate.now().getYear(); ---- >  This will get you your current year always
                .and().body("totalElement", is(equalTo(33)),
                        "content.id", hasItems(94, 98,91, 81),
                        "content.name", hasItems("Jocelin", "Georgianne", "Catie", "Marylee", "Elita"),
                        "content.gender", everyItem(is("Female")),
                        //"content.name", everyItem(containsString("e")),  // This is not working for validation with the containing "e" because there is a name which is "Elita"
                        "content.name", everyItem(containsStringIgnoringCase("e")))
                .log().all(); // logs all the response detail.

    }
}
