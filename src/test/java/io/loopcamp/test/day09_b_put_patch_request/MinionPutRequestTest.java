package io.loopcamp.test.day09_b_put_patch_request;

import io.loopcamp.util.MinionTestBase;
import java.util.HashMap;
import java.util.Map;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
public class MinionPutRequestTest extends MinionTestBase {


    /**
     Given accept type is json
     And content type is json
     And path param id is 15
     And json body is
         {
         "gender": "Male",
         "name": "PutRequest",
         "phone": 1234567425
         }
     When i send PUT request to /minions/{id}
     Then status code 204
     */


    @Test
    public void updateMinionTest() {
        Map <String, Object> reqMap = new HashMap<>();
        reqMap.put("gender", "Male");
        reqMap.put("name", "PutRequest");
        reqMap.put("phone", 1234567425);



        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)  // HEY api I am sending a data to you in this application/json format
                .and().body(reqMap)  /// HEY api, here is the data. --- > Serialization
                .and().pathParam("id", 133)  // Since we are updating already existing Minion, I need to provide id
                .when().put("/minions/{id}");


        response.prettyPrint();
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status Line: " + response.statusLine());
        assertThat(response.statusCode(), is(204));


    }
}
