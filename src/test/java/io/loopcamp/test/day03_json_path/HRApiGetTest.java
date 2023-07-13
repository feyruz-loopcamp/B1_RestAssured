package io.loopcamp.test.day03_json_path;

import io.loopcamp.util.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class HRApiGetTest {


    @BeforeEach
    public void setUp() {
//        RestAssured.baseURI = "http://44.204.229.13:1000/ords/hr";
//        RestAssured.baseURI = ConfigurationReader.getProperty("hr.api.url");
        baseURI = ConfigurationReader.getProperty("hr.api.url");  // Since I did static import of the RestAssured class, I can directly use any static methods or fields
    }


    /**
     * Given accept type is json
     * When user send get request to /ords/hr/regions
     * ------------
     * Status code should be 200
     * Content type should be "application/json"
     * And body should contain "Europe"
     */

    @DisplayName("GET /regions")
    @Test
    public void getRegionsTest() {

        Response response = given().accept(ContentType.JSON)
                .when().get("/regions");  // RestAssured already knows that baseUrl is in the request end point


        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        // assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.asString().contains("Europe"), "Europe is not in the response Body"); // The String part is the message if Europe is not in the body

    }


    /**
     * Given accept type is json
     * And Path param ‘region_id’ value is 10
     * When user send GET request to /regions/{region_id}
     * ---------------------------------
     * Status code should be 200
     * Content type should be "application/json"
     * And body should contain "Europe"
     */


    @DisplayName("GET /regions/{region_id} path param")
    @Test
    public void getRegionWithPathParamTest() {
        Response response = given().log().all().accept(ContentType.JSON) // with log().all() --- >  we can log all the details to see all info (like: url, header, params, etc.)
                .and().pathParam("region_id", 10)  // We can also assign value 10 to a variable and use the variable instead of 10
                .when().get("/regions/{region_id}");

        response.prettyPrint();


        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Europe"), "Wrong value is given");
    }


    /**
     * Given accept type is json
     * And query param q="{"region_name": "Americas"}"
     * When user send get request to /ords/hr/regions
     * ------------------------------------
     * Status code should be 200
     * Content type should be "application/json"
     * And region name should be "Americas"
     * And region id should be "20"
     */


    @DisplayName("GET /regions/ query param")
    @Test
    public void getRegionWithQueryParamTest() {


        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_name\": \"Americas\"}")
                //.accept(ContentType.JSON) // you can put it here as well. ORDER does NOT matter
                .when().get("/regions");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

//        And region name should be "Americas"
//        And region id should be "20"
        assertTrue(response.body().asString().contains("Americas"), "Region Name is not in the body");
        assertTrue(response.asString().contains("20"), "Region ID is incorrect");


    }


}
