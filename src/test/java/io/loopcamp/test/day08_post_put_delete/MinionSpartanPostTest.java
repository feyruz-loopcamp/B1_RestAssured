package io.loopcamp.test.day08_post_put_delete;

import io.loopcamp.pojo.Minion;
import io.loopcamp.util.MinionRestUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionSpartanPostTest {


    // We could have done storing this baseURI in another BaseBage but for only today testing we are using here directly.
    @BeforeAll
    public static void setUp () {
        baseURI = "http://34.207.85.246:8000/api";
    }

    /**
         given accept is json and content type is json
         and body is:
         {
         "gender": "Male",
         "name": "Feyruz",
         "phone": 1234567425
         }
         When I send POST request to /minions   ---- > we used /spartans
            --------------
         Then status code is 201
         And content type is json
         And "success" is "A Minion is Born!"    ---- > "A Spartan is Born!"
         Data name, gender , phone matches my request details
     */


    @DisplayName("POST new minion spartan using string json")
    @Test
    public void addNewMinionSpartanAsJasonTest() {

        // Put the double quote first and copy paste from the task. Then it will generate the / - forward slash automatically.
        String jsonBody = "{\n" +
                "         \"gender\": \"Male\",\n" +
                "         \"name\": \"Feyruz\",\n" +
                "         \"phone\": 1234567425\n" +
                "         }";

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)  // we set up the request body. We could have written it here directly as well.
                .when().post("/spartans");

        response.prettyPrint();

        System.out.println("Response code: " + response.statusCode());
        assertThat(response.statusCode(), is(201));
        assertEquals(201, response.statusCode());  // We just asserted with the JUnit assertions to practice. It is doing the same thing as above

        // verify the header
        assertThat(response.contentType(), is("application/json"));
        assertEquals("application/json", response.contentType());  // This is doing the same thing as above


        // assign the response to jsonPath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), is(equalTo("A Spartan is Born!")));
        assertThat(jsonPath.getString("data.name"), equalTo("Feyruz"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
        assertThat(jsonPath.getLong("data.phone"), equalTo(1234567425L));


        // extract the ID of newly added minion/spartan - and assign it to int variable
        int minionSpartanId = jsonPath.getInt("data.id");
//        System.out.println("ID before deleted: " + minionSpartanId);
//        given().pathParam("id", minionSpartanId)
//                .when().delete("/spartans/{id}")
//                .then().log().all();
        MinionRestUtil.deleteMinionById(minionSpartanId);

    }


    /**
     *     {
     *         "gender": "Male",
     *         "name": "Feyruz",
     *         "phone": 1234567425
     *     }
     */
    @DisplayName("POST new minion spartan using Map to JSON - SERIALIZATION")
    @Test
    public void addNewMinionSpartanAsMapTest () {

        Map <String, Object> minionSpartanPostMap = new HashMap<>();
        minionSpartanPostMap.put("gender", "Male");
        minionSpartanPostMap.put("name", "Feyruz");
        minionSpartanPostMap.put("phone", 1234567425);


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(minionSpartanPostMap)  // Jackson - serialization -- > Map to Json
                .when().post("/spartans");


        response.prettyPrint();

        System.out.println("Response code: " + response.statusCode());
        assertThat(response.statusCode(), is(201));
        assertEquals(201, response.statusCode());  // We just asserted with the JUnit assertions to practice. It is doing the same thing as above

        // verify the header
        assertThat(response.contentType(), is("application/json"));
        assertEquals("application/json", response.contentType());  // This is doing the same thing as above


        // assign the response to jsonPath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), is(equalTo("A Spartan is Born!")));
        assertThat(jsonPath.getString("data.name"), equalTo("Feyruz"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
        assertThat(jsonPath.getLong("data.phone"), equalTo(1234567425L));


        int minionSpartanId = jsonPath.getInt("data.id");
        MinionRestUtil.deleteMinionById(minionSpartanId);


    }


    /**
     *     {
     *         "gender": "Male",
     *         "name": "Feyruz",
     *         "phone": 1234567425
     *     }
     */
    @DisplayName("POST new minion spartan using Minion to JSON - SERIALIZATION")
    @Test
    public void addNewMinionSpartanAsPOJOTest () {
        Minion newMinion = new Minion();
//        System.out.println(newMinion.getId());
//        System.out.println(newMinion.getName());
        newMinion.setName("Feyruz");
        newMinion.setGender("Male");
        newMinion.setPhone("1234567425");

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newMinion)
               // .and().body(MinionRestUtil.getNewMinion())  // This will create a new Minion object each time we run this code
                .when().post("/spartans");

        response.prettyPrint();

        System.out.println("Response code: " + response.statusCode());
        assertThat(response.statusCode(), is(201));
        assertEquals(201, response.statusCode());  // We just asserted with the JUnit assertions to practice. It is doing the same thing as above

        // verify the header
        assertThat(response.contentType(), is("application/json"));
        assertEquals("application/json", response.contentType());  // This is doing the same thing as above

        // assign the response to jsonPath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), is(equalTo("A Spartan is Born!")));
        assertThat(jsonPath.getString("data.name"), equalTo("Feyruz"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
        assertThat(jsonPath.getLong("data.phone"), equalTo(1234567425L));

        int minionSpartanId = jsonPath.getInt("data.id");
        MinionRestUtil.deleteMinionById(minionSpartanId);


    }


    @DisplayName("POST new minion spartan using Minion to JSON - SERIALIZATION")
    @Test
    public void addNewMinionSpartanAsPOJOTWithUtilTest () {

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(MinionRestUtil.getNewMinion())  // This will create a new Minion object each time we run this code
                .when().post("/spartans");

        response.prettyPrint();

        System.out.println("Response code: " + response.statusCode());
        assertThat(response.statusCode(), is(201));
        assertEquals(201, response.statusCode());  // We just asserted with the JUnit assertions to practice. It is doing the same thing as above

        // verify the header
        assertThat(response.contentType(), is("application/json"));
        assertEquals("application/json", response.contentType());  // This is doing the same thing as above

        // assign the response to jsonPath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), is(equalTo("A Spartan is Born!")));
//        assertThat(jsonPath.getString("data.name"), equalTo("Feyruz"));
//        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
//        assertThat(jsonPath.getLong("data.phone"), equalTo(1234567425L));
//
        int minionSpartanId = jsonPath.getInt("data.id");
        MinionRestUtil.deleteMinionById(minionSpartanId);


    }



}




















