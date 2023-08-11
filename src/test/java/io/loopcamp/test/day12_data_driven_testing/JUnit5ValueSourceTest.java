package io.loopcamp.test.day12_data_driven_testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


import io.loopcamp.util.*;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class JUnit5ValueSourceTest {


    @ParameterizedTest    // This will help to run the method as many times as the data in source
    @ValueSource(ints = {5, 24, 45, 67, 687, 10, 23})  // this will help us to store data.
    public void numberTest (int num) {
        System.out.println("Num: " + num);
        assertThat(num, is(greaterThan(20)));
    }


    @ParameterizedTest
    @ValueSource (strings = {"Java", "API", "SQL", "JDBC", "RestAssured", "Selenium"})
    public void stringTest (String str) {
        System.out.println("Tool: " + str);
        assertThat(str, is(not((blankOrNullString()))));
    }


    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @ValueSource (ints = {22031, 20121, 98005, 07042, 33304, 64154, 22033, 65940})
    public void zipCodeTest (int zipcode) {

        given().accept(ContentType.JSON)
                .and().pathParam("postalCode", zipcode)
                .when().get("/us/{postalCode}")
                .then().assertThat().statusCode(200)
                .log().all();

    }

}
