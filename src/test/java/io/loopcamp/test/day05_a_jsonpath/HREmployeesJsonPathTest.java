package io.loopcamp.test.day05_a_jsonpath;




import io.loopcamp.util.HRApiTestBase;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;


public class HREmployeesJsonPathTest extends HRApiTestBase {

    /**
        Given accept type is Json
        And query param limit is 200
        when I send request to /employees
        then I can use JsonPath to query and read values from json body
     */

    @DisplayName("GET /employees?limit=10 - jsonPath filters")
    @Test
    public void jsonPathEmployeesTest () {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("limit", "200")
                .when().get("/employees");
        assertEquals(200, response.statusCode());


        // convert/parse the Response to JsonPath
        JsonPath jsonPath = response.jsonPath();
        System.out.println("First name of 1st emp: " + jsonPath.getString("items.first_name[0]"));
        System.out.println("ID of 1st emp: " + jsonPath.getString("items.employee_id[0]"));

        //how to get all employees email
        List <String> emailList = jsonPath.getList("items.email");
        System.out.println("All Emails: " + emailList);

        // Verify if JSEO is among the email options
        assertTrue(emailList.contains("JSEO"));


        // get all employees first name who work for depaertment_id 90
        // SELECT first_name FROM employees WHERE department_id = 90;
        // We can get all List and apply the filter on the response with the help pf JsonPath
        List <String> namesAtDep90 = jsonPath.getList("items.findAll{it.department_id==90}.first_name");
        System.out.println("Names, who work at Dep ID-90: " + namesAtDep90);


        //get all employees first names who work as IT_PROG (job_id)
        List <String> itProg = jsonPath.getList("items.findAll{it.job_id =='IT_PROG'}.first_name");
        System.out.println("It Prog job id employees: " + itProg);


        //get all employee first names whose salary is more than or equal 5000
        List<Integer> salaryMore5000 = jsonPath.getList("items.findAll{it.salary >= 5000}.first_name");
        System.out.println("Emp with salary more than or equal to 5000: " + salaryMore5000);

        //get emp first name who has max salary
        String maxSalary = jsonPath.getString("items.max{it.salary}.first_name");  // Since I am getting name and it is STRING, I NEED assign IT TO STRING
        System.out.println("Maximum Salary making employee: " + maxSalary );


        //get min salary
        int minSalary = jsonPath.getInt("items.min{it.salary}.salary");
        System.out.println("Minimum salary: " + minSalary);

        //get emp first name who has min salary
        String minSalaryFirstName = jsonPath.getString("items.min{it.salary}.first_name");
        System.out.println("First name with minimum salary: " + minSalaryFirstName);


    }


}
