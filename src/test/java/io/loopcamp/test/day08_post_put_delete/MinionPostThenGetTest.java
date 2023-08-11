package io.loopcamp.test.day08_post_put_delete;

import io.loopcamp.pojo.Minion;
import io.loopcamp.util.MinionRestUtil;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Map;


import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class MinionPostThenGetTest extends MinionTestBase {

    Minion newMinion = MinionRestUtil.getNewMinion();

    /**
     * Post a new minion with API POST
     * Then use GET method to get the info for the same minion
     * Then assert if they match
     * And DELETE that minion
     */

    @Test
    public void  postNewMinionThenGetTest () {
        System.out.println("New generated Minion: " + newMinion );

        //Post a new minion with API POST
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newMinion)  // serialization from POJO to json
                .when().post("/minions");

        response.prettyPrint();
        assertThat(response.statusCode(), is(HttpStatus.SC_CREATED));

        int newMinionID = response.jsonPath().getInt("data.id");


        // send GET request with the newMinionID and we can do assertion
        Map <String, Object> responseMap = MinionRestUtil.getMinionInMap(newMinionID);
        Minion newMinionFromGET = MinionRestUtil.getMinionInPOJO(newMinionID);
        System.out.println("GET request body in Map: " + responseMap);
        System.out.println("GET request body in Minion POJO: " + newMinionFromGET);



        // Then assert if they match
        // Expected: newMinion
        // Actual: newMinionFromGET

        assertThat(newMinionFromGET.getGender(), equalTo(newMinion.getGender()));
        assertThat(newMinionFromGET.getName(), equalTo(newMinion.getName()));
        assertThat(newMinionFromGET.getPhone(), equalTo(newMinion.getPhone()));


        //  And DELETE than minion
        MinionRestUtil.deleteMinionById(newMinionID);

    }

}
