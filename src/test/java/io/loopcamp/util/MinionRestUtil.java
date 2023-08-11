package io.loopcamp.util;

import com.github.javafaker.Faker;
import io.loopcamp.pojo.Minion;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class MinionRestUtil {


    // We could have done storing this baseURI in another BaseBage but for only today testing we are using here directly.
    @BeforeAll
    public static void setUp () {
        baseURI = "http://3.94.55.185:8000/api";
    }


    /**
     * @param id
     * This method is created by Loop Academy
     * It is deleting the Minion by the ID
     */
    public static void deleteMinionById (int id) {
        System.out.println("DELETE minion with id {" + id + "}");
        // this is a send DELETE request
        given().pathParam("id", id)
                .when().delete("/spartans/{id}")
                .then().log().all();
    }


    /**
     *
     * @return
     * This method is made by Loop Academy
     * This method return a new Minion with fake info
     */
    public static Minion getNewMinion () {
        Faker random = new Faker();  // this will help us to generate random data
        Minion newMinion = new Minion();

        newMinion.setName(random.name().firstName());  // set random first name
        newMinion.setPhone(random.number().numberBetween(1000000000L, 9999999999l)+"");


        int randomNum = random.number().numberBetween(1, 3);  // return either 1 or 2
        if (randomNum == 1) {
            newMinion.setGender("Female");
        } else  {
            newMinion.setGender("Male");
        }

        return newMinion;
    }


    /**
     * This method accepts minion id adn sends GET request
     * @param id
     * @return JSON response body as MAP
     */
    public static Map<String, Object> getMinionInMap (int id) {

        Response response = given().pathParam("id", id)
                .when().get("/minions/{id}");

        Map <String, Object> minionInMap = response.as(Map.class);
        return minionInMap;

    }


    /**
     * This method accepts minion id
     * @param id
     * @return JSON body as Minion object
     */
    public static Minion getMinionInPOJO (int id) {
        Response response = given().pathParam("id", id)
                .when().get("/minions/{id}");

        Minion minionInPojo = response.as(Minion.class);
        return minionInPojo;
    }


















}
