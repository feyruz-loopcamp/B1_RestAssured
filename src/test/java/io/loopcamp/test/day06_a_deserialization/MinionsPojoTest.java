package io.loopcamp.test.day06_a_deserialization;

import io.restassured.response.Response;
import io.loopcamp.pojo.Minion;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
public class MinionsPojoTest extends MinionTestBase {

    /**
         Given accept type is json
         when I send GET request to /minions
            ---------------
         Then status code is 200
         And content type is json
         And I can convert json to list of minion pojos
     */


    @Test
    public void allMinionsToPojoTest () {

        Response response = given().accept(ContentType.JSON)
                .when().get("/minions");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());


        // How can I convert response to jsonPath
        JsonPath jsonPath = response.jsonPath();

        //List <Map<String, Object>> allMinions2 = response.body().as(List.class);   //  This is doing the same thing as below but we used POJO class since we have Minion.class

        // Using jsonPath extract List of minions / deserialization
        List <Minion> allMinions = jsonPath.getList("", Minion.class); // We converted json array/list into a List of Minion POJO object
                                                                            // "" -- > start from beginning in Json response body like from  {


        // How can I ge tthe size of how many Minions we have
        System.out.println("Count of Minions: " + allMinions.size());


        // Get the first minion
        System.out.println("First Minion: " + allMinions.get(0));


        // Can you get all the Female minions and store it in another List.
        List <Minion> femaleMinions = new ArrayList<>();
        for ( Minion each : allMinions) {
            if (each.getGender().equals("Female")) {
                femaleMinions.add(each);
            }
        }
        System.out.println("All Female Minions: " + femaleMinions);

        // We can use stream to filter and store the result
        List<Minion> femaleMinions2 = allMinions.stream()
                .filter(each -> each.getGender().equals("Female"))
                .collect(Collectors.toList());
        System.out.println("Female Minions: " + femaleMinions2);


        // How to get how many Female minions there are?
        System.out.println("Count of Female Minions: " + femaleMinions.size());


        // Get me all the Male Minions
        List <Minion> allMales= new ArrayList<>();
        // With Lambda expression
        allMinions.forEach(p->{
            if(p.getGender().equals("Male")){
                allMales.add(p);
            }
        });

        // with traditional loop
        List <Minion> maleMinions = new ArrayList<>();
        for ( Minion each : allMinions ) {
            if (each.getGender().equals("Male")){
                maleMinions.add(each);
            }
        };
        System.out.println("All male minions: " + maleMinions);

        // With stream
        List<Minion> maleMinions2 = allMinions.stream()
                .filter(each -> each.getGender().equals("Male"))
                .collect(Collectors.toList());

        // how can I get the count of Male Minions
        System.out.println("All Male Minion Count: " + maleMinions2.size());


        // This is another way to get the count with stream
        long countMale = allMinions.stream().filter(each -> each.getGender().equals("Male")).count();  // count(); method returns long data type. That is we stored it into long variable
        System.out.println("All Male Minion Number: " + countMale );

    }

}
