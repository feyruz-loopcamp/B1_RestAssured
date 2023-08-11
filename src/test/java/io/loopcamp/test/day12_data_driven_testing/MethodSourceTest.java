package io.loopcamp.test.day12_data_driven_testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.BeforeAll;
import io.loopcamp.util.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MethodSourceTest {



    public static List<String> getCountries () {
        List <String> countries = Arrays.asList("Canada", "USA", "Azerbaijan", "Turkey");
        return countries;
    }


    @ParameterizedTest
    @MethodSource("getCountries")
    public void countriesTest(String eachCountry) {
        System.out.println("Country: " + eachCountry);
        System.out.println("Length: " + eachCountry.length());

    }


}
