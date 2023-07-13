package io.loopcamp.test.day02_a_headers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionHeaderTest {


    /**
     * 	When I send a GET request to
     * 		minion_base_url/api/minions
     * 	Then Response STATUS CODE must be 200
     * 	And I should see all Minions in XML format
     */


    String url = "http://44.204.229.13:8000/api/minions";


    @DisplayName("GET api/minions and expect XML response header")
    @Test
    public void  getAllMinionsApiTest() {
        when().get(url)  // request is send here
                .then().assertThat().statusCode(200) // asserting the response status code
                .and().contentType(ContentType.XML);  // ContentType.XML -- > is enum
                //.and().contentType("application/xml"); // By default application is sending the xml format

    }


    /**
     * Given Accept Type is application/json
     * 	When I send a GET request to
     * 		minion_base_url/api/minions
     * 	----------------------------------------
     * 	Then Response STATUS CODE must be 200
     * 	And I should see all Minions in JSON format
     */

    @DisplayName("GET api/minions with requested header - JSON")
    @Test
    public void acceptTypeHeaderTest() {
        given().accept(ContentType.JSON)
                .when().get(url)
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON);
    }


    // There is a way also to read the RESPONSE HEADER
    /**
     * 	Given Accept type application/json
     * 	When user send GET request to api/minions end point
     * 	-----------------------------------------
     * 	Then Response STATUS CODE must be 200
     * 	And response Content Type must be application/json
     * 	And read headers
     */


    @DisplayName("GET api/minions - read Headers")
    @Test
    public void readResponseHeaders () {
        Response response = given().accept(ContentType.JSON)
                .when().get(url); // I send the request and saved the response into 'response' reference

        System.out.println("Date Header: " + response.getHeader("Date")); // similar to Map.get("key")
        System.out.println("Content Type: " + response.getHeader("Content-Type"));
        System.out.println("Connection: " + response.getHeader("Connection"));
        System.out.println();

        // ead all headers
        System.out.println("Headers: " + response.getHeaders());

        // This is doing the same thing but we assign it to Headers class and used that object
        Headers headers =  response.getHeaders();
        System.out.println("Headers 2: " + headers);

        // can you ensure that Keep-Alive is present
        //assertTrue(response.getHeader("Keep-Alive") != null);

        // This is doing the same thing with a different assertion.
        assertNotNull(response.getHeader("Keep-Alive"));

    }



}










