package io.loopcamp.test.day05_a_jsonpath;

import io.loopcamp.util.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class ZipCodeApiJsonPathTest {

    /**
         Given accept type is json
         and country path param value is "us"
         and postal code path param value is 22102
         When I send get request to http://api.zippopotam.us/{country}/{postal-code}
        --------------------------------------
         Then status code is 200
         Then "post code" is "22102"
         And  "country" is "United States"
         And "place name" is "Mc Lean"
         And  "state" is "Virginia"
     */

    @BeforeAll
    public static void setUp () {
        System.out.println("Setting up base url....");
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }


    @DisplayName("GET us/zipcode - jsonPath")
    @Test
    public void zipCodeJsonPathTest () {

        Response response = given().accept(ContentType.JSON)
                //.and().pathParams("country", "us", "zip", "22102")
                .and().pathParam("country", "us")
                .and().pathParam("zip", "22102")
                .when().get("/{country}/{zip}");

        response.prettyPrint();


        assertEquals(HttpStatus.SC_OK, response.statusCode());


        /*
             Then "post code" is "22102"
             And  "country" is "United States"
             And "place name" is "Mc Lean"
             And  "state" is "Virginia"
         */

        //assertEquals("22102", response.path("post code"));

        //first, we need to assign response json payload/body to JsonPath
        JsonPath jsonPath = response.jsonPath();


        // validate the post code matched
        System.out.println("Post Code: " + jsonPath.getString("'post code'")); // When you have a space in the KEY, make sure you provide additional single quote around KEY
        assertEquals("22102", jsonPath.getString("'post code'"));
        System.out.println("Country Name: " + jsonPath.getString("country"));
        assertEquals("United States", jsonPath.getString("country"));

        // Verify the place name
        System.out.println("Place Name: " + jsonPath.getString("places.'place name'[0]"));  // since there is a space, DO NOT forget the single quote
        //System.out.println("Place Name: " + jsonPath.getString("places[0].'place name'"));  // This is giving the same value as above
        assertEquals("Mc Lean", jsonPath.getString("places.'place name'[0]"));


        // Verify state name
        System.out.println("State Name: " + jsonPath.getString("places[0].state"));
        assertEquals("Virginia", jsonPath.getString("places[0].state"), "The state is invalid" );


        // jsonpath can help use to be used in a reusable methods
        verifyZipCode(jsonPath, "22102");


    }


    public void verifyZipCode (JsonPath jsonPath, String expZipCode) {
        assertEquals(expZipCode, jsonPath.getString("'post code'"));
    }
















}
