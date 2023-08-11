package io.loopcamp.test.day11_c_specifications;

import io.loopcamp.util.MinionSecureTestBase;

import io.loopcamp.util.*;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class MinionSpecTest extends MinionSecureTestBase {



    RequestSpecification reqSpec = given().accept(ContentType.JSON)
            .and().auth().basic(
                    ConfigurationReader.getProperty("minion.api.username"),
                    ConfigurationReader.getProperty("minion.api.password"));


    ResponseSpecification resSpec = expect().statusCode(is(200))
            .and().contentType("application/json");


    /**
     * Get all minions
     */

    @Test
    public void allMinionsTest () {

//        given().accept(ContentType.JSON)
//                .and().auth().basic(
//                        ConfigurationReader.getProperty("minion.api.username"),
//                        ConfigurationReader.getProperty("minion.api.password"))
        given().spec(reqSpec) // this replaced the one above
                .when().get("/minions")



//                .then().assertThat().statusCode(is(200))
//                .and().contentType("application/json");
                .then().spec(resSpec)// this replace the one above
                .log().all();

    }


    /**
     * Get Single Minion
     */
    @Test
    public void singleMinionTest () {

        given().spec(reqSpec)
                .and().pathParam("id", 15)
                .when().get("/minions/{id}")
                .then().spec(resSpec)
                .and().body("name", equalTo("PutRequest"))  // you can still add some other assertion/verificaiton
                .log().all();

    }
}
