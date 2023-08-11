package io.loopcamp.test.day09_b_put_patch_request;

import java.util.HashMap;
import java.util.Map;

import io.loopcamp.util.MinionRestUtil;
import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class MinionPatchRequestTest extends MinionTestBase {

    /**
     PATCH /api/minions/{id}
     Partially Update A Minion

     Given accept type is json
     And content type is json
     And path param id is 15
     And json body is
         {
         "phone": 1234567425
         }
     When i send PATCH request to /minions/{id}
     Then status code 204
     */

    @Test
    public void minionPatchTest() {
        Map <String, Object> patchMap = new HashMap<>();
        patchMap.put("phone", 1234567400);

        int minID = 15;

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(patchMap)
                .and().pathParam("id", minID)
                .and().patch("/minions/{id}")
                .then().assertThat().statusCode(is(204));


        //How can I modify my test to get the updated minion
        // TO get the minion --- > id
        Map <String, Object> minion = MinionRestUtil.getMinionInMap(minID);
        System.out.println(minID + " Minion info: " + minion);

        assertThat(minion.get("phone"), equalTo(patchMap.get("phone")+""));




    }


}
