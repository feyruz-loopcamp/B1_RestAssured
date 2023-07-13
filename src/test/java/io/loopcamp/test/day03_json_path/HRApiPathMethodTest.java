package io.loopcamp.test.day03_json_path;

import io.loopcamp.util.HRApiTestBase;
import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;


public class HRApiPathMethodTest extends HRApiTestBase {


    /**
     * Given accept is json
     * When I send GET request to /countries
     * ----------
     * Then status code is 200
     * And content type is json
     * Verify navigating through json array
     * Verify 1st country id is AR
     * Verify 1st country name is Argentina
     */



    @DisplayName("GET /countries with path()")
    @Test
    public void readCountriesUsingPathTest() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("ct", "countries")
                .when().get("/{ct}");
               // .when().get("/countries");


        assertEquals(HttpStatus.SC_OK, response.statusCode());
       // assertEquals(200, response.statusCode());
        /*
            {
                "items": [
                                {
                                    "country_id": "AR",
                                    "country_name": "Argentina",
                                    "region_id": 20,
                                    "links": [
                                        {
                                            "rel": "self",
                                            "href": "http://44.204.229.13:1000/ords/hr/countries/AR"
                                        }
                                    ]
                                }
            }
         */
        // GEt me the first country_id
        System.out.println("First country ID: " + response.path("items[0].country_id"));


        // GET me the first county_name
        System.out.println("First country Name: " + response.path("items[0].country_name"));


        assertEquals("AR", response.path("items[0].country_id"), "Id is not available.");
        assertEquals("Argentina", response.path("items[0].country_name"),  "Country name is invalid");


        // Store all the county names into a List
        List <String> allCountryNames = response.path("items.country_name");
        System.out.println("All Country Names: " + allCountryNames);

    }


}
