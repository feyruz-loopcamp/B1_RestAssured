package io.loopcamp.test.day12_data_driven_testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


import io.loopcamp.util.*;

import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class CsvSourceDDTTest {


    @ParameterizedTest
    @CsvSource ({
            "7, 5, 2, 14",
            "34, 55, 77, 166",
            "22, 8, 12, 42",
            "34, 11, 90, 135"
    })
    public void basicAddTest (int num1, int num2, int num3, int actSum) {
        System.out.println("Num 1 - " + num1);
        System.out.println("Num 2 - " + num2);
        System.out.println("Num 3 - " + num3);

        int sum = num1 + num2 + num3;
        System.out.println("Sum of nums: " + sum);


        int expSum = sum;
        assertThat(actSum, equalTo(expSum));

    }



    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @CsvSource({"New York City, NY",
            "Denver, CO",
            "Boston, MA",
            "East Pittsburgh, PA",
            "Raleigh, NC",
            "San Diego, CA",
            "Baltimore, MD",
            "Fairfax, VA"
    })
    public void cityAndStateXipCodeAPITest (String city, String state) {

        Map <String, String> params = new HashMap<>();

        params.put("state", state);
        params.put("city", city);

        given().accept(ContentType.JSON)
                .and().pathParams(params)
                .when().get("/us/{state}/{city}")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .log().all();

    }





}
