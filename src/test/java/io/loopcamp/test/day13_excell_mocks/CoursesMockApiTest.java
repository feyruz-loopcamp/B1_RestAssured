package io.loopcamp.test.day13_excell_mocks;

import io.loopcamp.util.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class CoursesMockApiTest {

    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("mock_server_api");
    }


    /**
         Given accept type is json
         When I send get request to /courses
         Then status code is 200
         And content type is json
         And body courseIds contain 1,2,3
         And courseNames are "Java SDET", "RPA Developer", "Salesforce Automation"
     */

    @DisplayName("GET /courses - mock api ")
    @Test
    public void courseMockApiTest () {

        given().accept(ContentType.JSON)
                .when().get("/courses")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("courseId", hasItems(1, 2, 3),
                "courseName", hasItems("Java SDET", "RPA Developer", "Salesforce Automation"))
                .log().all();
    }
}

