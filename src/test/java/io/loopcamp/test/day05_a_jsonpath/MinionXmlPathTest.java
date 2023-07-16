package io.loopcamp.test.day05_a_jsonpath;

import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionXmlPathTest extends MinionTestBase {

    /**
        Given accept Type is application/xml
        When I send GET request to /minions
     ---------------------------------------
        Then status code is 200
        And content type is XML
        And minion at index 0 is matching
             "id": 1,
             "gender": "Male",
             "name": "Meade",
             "phone": "3584128232"
     */

    @DisplayName(" GET /minions - xmlPath")
    @Test
    public void minionXMLPathTest () {

        Response response = given().accept(ContentType.XML)
                .and().get("/minions");

        //response.prettyPrint();
        assertEquals(200, response.statusCode());
        assertEquals(ContentType.XML.toString(), response.contentType());


        XmlPath xmlPath = response.xmlPath();
        System.out.println("id:" + xmlPath.getInt("List.item[0].id"));
        System.out.println("gender:" + xmlPath.getString("List.item[0].gender"));
        System.out.println("name:" + xmlPath.getString("List.item[0].name"));
        System.out.println("phone:" + xmlPath.getString("List.item[0].phone"));

        // How can I get all names with xmlPath
        List <String> allName = xmlPath.getList("List.item.name");
        System.out.println("All names: " + allName);

        assertEquals(1, xmlPath.getInt("List.item[0].id"));
        assertEquals("Male", xmlPath.getString("List.item[0].gender"));
        assertEquals("Meade", xmlPath.getString("List.item[0].name"));
        assertEquals(3584128232L, xmlPath.getLong("List.item[0].phone"));  // asserting the Long data type


    }
}
