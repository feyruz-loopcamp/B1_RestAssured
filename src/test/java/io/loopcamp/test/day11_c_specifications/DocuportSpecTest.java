package io.loopcamp.test.day11_c_specifications;

import io.loopcamp.util.ConfigurationReader;
import io.loopcamp.util.DocuportApiTestBase;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DocuportSpecTest extends DocuportApiTestBase {

    /**
         Given accept type is json
         And header Authorization is access_token of employee title
         When I send GET request to /api/v1/company/organizer-titles/all
         Then status code is 200
         And content type is json
         And employee title info is presented in payload
     */

    String accToken = getAccessToken(
            ConfigurationReader.getProperty("employee_email"),
            ConfigurationReader.getProperty("employee_password"));

    RequestSpecification employeeReqSpec = given().accept(ContentType.JSON)
            .and().header("Authorization", accToken) // we could have put the token here directly: Bearer ..... but we want to make it dynamic. Because token may change
            .log().all();

    ResponseSpecification responseSpec = expect().statusCode(200)
            .and().contentType(ContentType.JSON)
            .logDetail(LogDetail.ALL);


    @Test
    public void employeeInfoTest() {
        List<Map<String, Object>> titleList = given().spec(employeeReqSpec)
                .when().get("/api/v1/company/organizer-titles/all")
                .then().spec(responseSpec)
                .and().extract().body().as(List.class);

        System.out.println(titleList);

        Assertions.assertEquals("1", titleList.get(0).get("value") );
        Assertions.assertEquals("President", titleList.get(0).get("displayText"));


        //by using hamcreset matchers
        given().spec(employeeReqSpec)
                .when().get("/api/v1/company/organizer-titles/all")
                .then().spec(responseSpec)
                .and().body("value[0]",       is("1"),
                        "displayText[0]", is("President"));

    }
}
