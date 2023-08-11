package io.loopcamp.test.day13_excell_mocks;
import io.loopcamp.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
public class MockStudentApiTest {


    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("mock_server_api");
    }


    @DisplayName("GET /students/1 -- mock api server")
    @Test
    public void testStudent () {

        given().accept(ContentType.JSON)
                .when().get("/students/1")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .log().all();
    }

}
