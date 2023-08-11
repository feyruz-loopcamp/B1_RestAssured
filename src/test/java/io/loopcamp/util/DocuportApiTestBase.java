package io.loopcamp.util;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class DocuportApiTestBase {


    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("docuport.base.url");
    }


    // Create one method that GETs access token.
    public static String getAccessToken (String uN, String pW) {


        String jsonBody = "{\n" +
                "\"usernameOrEmailAddress\": \"" + uN + "\",\n" +
                "\"password\": \"" + pW + "\"\n" +
                "}";


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/api/v1/authentication/account/authenticate");

// You can do it this was as well and return accessToken
//        String accessToken = given().accept(ContentType.JSON)
//                .and().contentType(ContentType.JSON)
//                .and().body(jsonBody)
//                .when().post("/api/v1/authentication/account/authenticate")
//                .then().assertThat().statusCode(is(200))
//                .extract().path("user.jwtToken.accessToken");

        assertThat(response.statusCode(), is(HttpStatus.SC_OK));
        String accessToken = response.path("user.jwtToken.accessToken");


        Assertions.assertTrue(accessToken != null && !accessToken.isEmpty());

        return accessToken;

    }



}
