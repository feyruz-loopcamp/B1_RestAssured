package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class HelloWorldApiTest {
    String url = "https://sandbox.api.service.nhs.uk/hello-world/hello/world";

    @DisplayName("Hello World GET request")
    @Test
    public void helloWorldGetRequestTest(){
        //send a GET request and save response inside the Response object
        Response response = RestAssured.get(url);

        response.prettyPrint();
        System.out.println(response.body().asString());
        System.out.println(response.asString());

        Assertions.assertTrue(response.asString().contains("Hello World!"));

    }

}