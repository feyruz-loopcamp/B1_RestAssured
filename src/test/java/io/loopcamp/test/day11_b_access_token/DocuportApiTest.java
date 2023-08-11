package io.loopcamp.test.day11_b_access_token;

import io.loopcamp.util.ConfigurationReader;
import io.loopcamp.util.DocuportApiTestBase;
import io.loopcamp.pojo.Minion;

import io.loopcamp.util.MinionTestBase;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class DocuportApiTest extends DocuportApiTestBase {

    /**
         Given accept type is json
         And header Authorization is access_token of client
         When I send GET request to  /api/v1/company/counties/all
         Then status code is 200
         And content type is json
                application/json; charset=utf-8
         And counties info is presented in payload
            "Ken County"
            "Caroline County"

     */


    @Test
    public void getAllCountiesTest() {

//        String accessTok = getAccessToken("b1g1_client@gmail.com", "Group1");
        String accessTok = getAccessToken(
                ConfigurationReader.getProperty("client_email"),
                ConfigurationReader.getProperty("client_password"));

        Response response = given().accept(ContentType.JSON)
                .and().header("Authorization", accessTok)
                .when().get("/api/v1/company/counties/all");


        response.prettyPrint();
        assertThat(response.statusCode(), is(200));
        assertThat(response.contentType(), is(equalTo("application/json; charset=utf-8")));
        assertThat(response.path("name"), hasItems("Kent County", "Caroline County"));


    }



    /**
         * Given accept type is Json
         * And header Authorization is access token of supervisor
         * -- No need this since there is no endpoint developed with path param-- And path param "id" is "155"
         * When I send GET request to "/api/v1/company/states/all/"
     *          -----------
         * Then status code is 200
         * And content type is json
         * And state "Washington D.C." is in the response body
         * -- No need this -- And sates has items "" , "", "", ""
     */

    @Test
    public void getAllStatesTest () {

        String accTok = getAccessToken(
                ConfigurationReader.getProperty("supervisor_email"),
                ConfigurationReader.getProperty("supervisor_password")
        );
        System.out.println("Acc Token: " + accTok);


        given().accept(ContentType.JSON)
                .and().header("Authorization", accTok)
                .when().get("/api/v1/company/states/all/")
                .then().assertThat().statusCode(is(200))
                .and().assertThat().contentType("application/json; charset=utf-8 ")
                .and().body("name", hasItems("Washington D.C."));

    }



    /**
     * Given accept type is Json
     * And header Authorization is access token of employee
     * When I send GET request to /api/v1/company/industries/all
     * Then status code is 200
     * And content type is json
     body matches data:
         {
             "value": 2,
             "displayText": "Agents",
         }
     */

    @Test
    public void industryInfo () {

        String accToken = getAccessToken(
                ConfigurationReader.getProperty("employee_email"),
                ConfigurationReader.getProperty("employee_password"));

        System.out.println("Employee Token: " + accToken);


        List <Map <String, Object>> industryList = given().accept(ContentType.JSON)
                .and().header("Authorization", accToken)
                .when().get("/api/v1/company/industries/all")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType("application/json")
                //.log().all()
                .and().extract().body().as(List.class);


        System.out.println("Industry List: " + industryList);;

        assertThat(industryList.get(1).get("value"), equalTo("2"));
        assertThat(industryList.get(1).get("displayText"), equalTo("Agents"));


    }

}










