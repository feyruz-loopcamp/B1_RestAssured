package io.loopcamp.test.day07_a_hamcrest;


import com.github.javafaker.Country;
import io.loopcamp.util.HRApiTestBase;
import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

public class ORDSHamcrestTest extends HRApiTestBase {

    /**
          given accept type is json
          when I send GET request to /countries
            -----------------
          Then status code is 200
          and content type is json
          and count should be 25
          and country ids should contain "AR,AU,BE,BR,CA"
          and country names should have "Argentina,Australia,Belgium,Brazil,Canada"

          items[0].country_id ==> AR
          items[1].country_id ==> AU
     */

    @DisplayName("GET /countries  --> hamcrest assertion")
    @Test
    public void countriesTest() {

        given().accept(ContentType.JSON)
                .when().get("/countries")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("count", is(25),
                        "items.country_id", hasItems("AR", "AU", "BE", "BR", "CA"),
                        "items.country_name", hasItems("Argentina", "Australia", "Belgium", "Brazil", "Canada"),
                        "items[0].country_id", equalTo("AR"),
                        "items[1].country_id", is(equalTo("AU")));

    }



    @Test
    public void countriesExtractTest() {

        String countryId = given().accept(ContentType.JSON)
                .when().get("/countries")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("count", is(25),
                        "items.country_id", hasItems("AR", "AU", "BE", "BR", "CA"),
                        "items.country_name", hasItems("Argentina", "Australia", "Belgium", "Brazil", "Canada"),
                        "items[0].country_id", equalTo("AR"),
                        "items[1].country_id", is(equalTo("AU")))
                .log().everything()
                .and().extract().body().path("items[0].country_id");   // with the help of this method, it will return me the specific VALUE of the given KEY


        System.out.println("Country ID: " + countryId);



        // The following task is basically we are linking two tests together by extracting a value from first call and using it in the second call
        /**
         *   * given accept type is json
         *      * when I send get request to /countries/{country_id}
         *      ---------------------
         *      * Then status code is 200
         *      * and content type is json
         *      And country_name is Argentina, country_id is AR, region_id is 20
         */

        given().accept(ContentType.JSON)
                .and().pathParam("country_id", countryId)
                .when().get("/countries/{country_id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("country_name", equalTo("Argentina"),
                        "country_id", is(equalTo("AR")),
                        "region_id", equalTo(211));
                //and().extract().jsonPath();  // with the help of extract() method you can extract it in different options

    }


}

























