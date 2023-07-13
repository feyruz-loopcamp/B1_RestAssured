package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class HelloWorldApiTest {

    /**
     When user sends GET Request to :
        https://sandbox.api.service.nhs.uk/hello-world/hello/world
     Then status code should be 200
     And "Hello World!" message should be in the response
     */
    String url = "https://sandbox.api.service.nhs.uk/hello-world/hello/world";

    @DisplayName("Hello World GET request")
    @Test
    public void helloWorldGetRequestTest(){
        //send a GET request and saved response inside the Response object
        Response response = RestAssured.get(url);

        //Here we just printed the response body
        response.prettyPrint();
//        System.out.println(response.body().asString());  // This is doing the same thing as above
//        System.out.println(response.asString());  // This is doing the same thing as above


        // How can I validate the Status Code
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status Line: " + response.statusLine());


        // assert that status code is 200
        //Assertions.assertTrue(response.statusCode() == 200);
        Assertions.assertEquals(200, response.statusCode());

        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200, actualStatusCode);


        // assert that the "Hello World! is in the response.
        Assertions.assertTrue(response.asString().contains("Hello World!"));

    }

}