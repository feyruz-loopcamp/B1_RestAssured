package io.loopcamp.test.day12_data_driven_testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.*;


import io.loopcamp.util.*;

import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class CsvFileSourceTest {

    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }


    @ParameterizedTest
    @CsvFileSource (resources = "/ZipCodes.csv", numLinesToSkip = 1)
    public void zipCodeTest (String state, String city, int zip_count) {

        Map <String, String>  params = new HashMap<>();
        params.put("state", state);
        params.put("city", city);

        given().accept(ContentType.JSON)
                .and().pathParams(params)
                .when().get("/us/{state}/{city}")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().body("places", hasSize(zip_count))  // Integer.valueOf()
                .log().all();
    }
}
















