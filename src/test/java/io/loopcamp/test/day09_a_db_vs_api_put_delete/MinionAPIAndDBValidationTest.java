package io.loopcamp.test.day09_a_db_vs_api_put_delete;

import io.loopcamp.util.*;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import io.loopcamp.util.MinionRestUtil;



/**
     given accept is json and content type is json
     and body is:
         {
             "gender": "Male",
             "name": "PostVSDatabase"
             "phone": 1234567425
         }
     When I send POST request to /minions
     Then status code is 201
     And content type is json
     And "success" is "A Minion is Born!"

     When I send database query
         SELECT name, gender, phone
         FROM minions
         WHERE minion_id = newIdFrom Post request;
     Then name, gender , phone values must match with POST request details
 */
public class MinionAPIAndDBValidationTest extends MinionTestBase {


    @Test
    public void postNewMinionThenValidateInDBTest() {
        Map <String, Object> postReqMap = new HashMap<>();
        postReqMap.put("gender", "Male");
        postReqMap.put("name", "PostVSDatabase");
        postReqMap.put("phone", 1234567425);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(postReqMap)
                .when().post("/minions");

        response.prettyPrint();


        assertThat(response.statusCode(), equalTo(HttpStatus.SC_CREATED));
        assertThat(response.contentType(), equalTo("application/json;charset=UTF-8"));


        // Convert response to jsonPath
        JsonPath jsonPath = response.jsonPath();
        ///assertThat(response.path("success"), is(equalTo("A Minion is Born!")));  // this is doing the same thing
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));

        int minionID = jsonPath.getInt("data.id");
        System.out.println("Minion ID: " + minionID);


        String query = "SELECT name, gender, phone FROM minions WHERE minion_id = " + minionID;


        //Adding connection data from configuration properties
        String dbUrl = ConfigurationReader.getProperty("minion.db.url");
        String dbUser = ConfigurationReader.getProperty("minion.db.username");
        String dbPw = ConfigurationReader.getProperty("minion.db.password");



        // Connect to DB
        DBUtils.createConnection(dbUrl, dbUser, dbPw);
        // Calling a method which will run our query and return the result row as a Map
        Map <String, Object> dbMap = DBUtils.getRowMap(query);
        System.out.println("dbMap result: " + dbMap);


        // Now need to compare db data is matching API post data
        //Expected: the Map that was used as body of the POST call is the expected Map
        //Actual: the Map that we store data from db query result is the actual Map
        assertThat(dbMap.get("NAME"), equalTo(postReqMap.get("name")));
        assertThat(dbMap.get("GENDER"), equalTo(postReqMap.get("gender")));
        assertThat(dbMap.get("PHONE"), equalTo(postReqMap.get("phone").toString()));


        // Deleting the same Minion
        MinionRestUtil.deleteMinionById(response.jsonPath().getInt("data.id"));


    }
}
