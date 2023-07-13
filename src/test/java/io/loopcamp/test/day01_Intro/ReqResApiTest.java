package io.loopcamp.test.day01_Intro;


import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class ReqResApiTest {

    String url = "https://reqres.in/api/users";


    /**
     When User sends GET Request to
     https://reqres.in/api/users
     Then Response status code should be 200
     And Response body should contain "George"
     And Header Content type should be application/json
     */
    @DisplayName("GET all users")  // This is coming from JUnit 5 - It is just to show the name ing nicely on IntelliJ Report
    @Test
    public void userGetTest () {


        //When User sends GET Request to --- > https://reqres.in/api/users
        //Response response = RestAssured.get(url);   // This one we used with the Class name and called the static method.
        Response response = when().get(url);  // Since we did the static import, we can use the method directly


        //Then Response status code should be 200
        System.out.println("Status Code: " + response.statusCode());
        assertEquals(200, response.statusCode());

        // BDD Syntax
        response.then().statusCode(200);  // method chaining
        response.then().assertThat().statusCode(200);  // more method chaining - more readable


        // And Response body should contain "George"
        System.out.println("Response json body: " + response.asString());  // asString() method will put everything in one line
        System.out.println("Response json body: " );
        response.prettyPrint();

        assertTrue(response.asString().contains("George"));  // We will learn something called Hamcrest Matchers for better body json body validation.



       // And Header - Content type should be json
        System.out.println("Content Type header value: " + response.contentType());
        assertTrue(response.contentType().contains("application/json"));

    }


    /** NEW TEST CASE
         When User Sends get request to API Endpoint:
            "https://reqres.in/api/users/5"
         Then Response status code should be 200
         And Response body should contain user info "Charles"
     */

    @DisplayName("GET single user")
    @Test
    public void getSingleUserApiTest(){

        //When User Sends get request to API Endpoint: --- > "https://reqres.in/api/users/5"
        Response response = given().get(url+"/5");  // Here we made a concatenation with the url

        // Then Response status code should be 200
        System.out.println("Status code: " + response.statusCode());
        assertEquals(200, response.statusCode());


        //And Response body should contain user info "Charles"
        response.prettyPrint();
        assertTrue(response.asString().contains("Charles"));

    }

    /** Negative test case
         When Send get request to API Endpoint:
            "https://reqres.in/api/users/50"
         Then Response status code should be 404
         And Response body should contain "{}"
     */

    @DisplayName("GET request to non existing user")
    @Test
    public void getSingleUserNegativeApiTest(){

        Response response = given().get(url + "/50");

        System.out.println("Status code: " + response.statusCode());
        assertEquals(404, response.statusCode());


        System.out.println("Json response body: " + response.asString());
        assertEquals("{}", response.asString(), "Not matching json response body");
//        assertEquals("{]", response.asString(), "Not matching json response body");


    }


}
