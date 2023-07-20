package io.loopcamp.test.day06_a_deserialization;

import io.loopcamp.pojo.MinionSearch;
import io.restassured.response.Response;
import io.loopcamp.pojo.Minion;
import io.loopcamp.util.ConfigurationReader;

import io.loopcamp.util.HRApiTestBase;
import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.commons.math3.analysis.function.Min;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;


public class MinionSearchPojoTest extends MinionTestBase {

    /**
         Given accept type is json
         And query param nameContains value is "e"
         And query param gender value is "Female"
         When I send get request to /minions/search
            -----------------
         Then status code is 200
         And content type is Json
         And json response data is as expected
     */

    @Test
    public void minionSearchPojoTest () {
        Map <String, String> queryPar = new HashMap<>();
        queryPar.put("nameContains", "e");
        queryPar.put("gender", "Female");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryPar)
                .when().get("/minions/search");

        //response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());


        //deserialize json response/payload body to MinionSearch pojo class
        MinionSearch minionSearch = response.body().as(MinionSearch.class);

        // Total elements
        System.out.println("Total Element: " + minionSearch.getTotalElement());
        System.out.println("All Minions with gender Female and name containing \"e\": " + minionSearch.getContent());
        System.out.println("First Female Minion with name containing \"e\": " + minionSearch.getContent().get(0));


        // Get me the 2nd Female Minion all info.
        Minion secondMinion = minionSearch.getContent().get(1);  // This is Minion object
        System.out.println("Second Minion id: " + secondMinion.getId());
        System.out.println("Second Minion id: " + minionSearch.getContent().get(1).getId());
        System.out.println("Second Minion name: " + secondMinion.getName());


        List <Minion> allMinionContent = minionSearch.getContent();

        //Get all the name fo Minions and store it into a List
        List <String> allFemaleMinionsName = new ArrayList<>();
        for (Minion each : allMinionContent) {
            allFemaleMinionsName.add(each.getName());
        }

        System.out.println("All Female Minion Names: " + allFemaleMinionsName);

        // Can you store all names into a List with the stream (Functional Interface)
        List <String> allName = allMinionContent.stream().map(each -> each.getName()).collect(Collectors.toList());
        System.out.println("All names with stream: " + allName);


    }


}













