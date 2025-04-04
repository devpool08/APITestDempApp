package com.demo_api.APITestDempApp.test_todo_controller;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class TestTodoController {
    private static final String REQUEST_PATH="/todo";
    private static int countId=1;
    @BeforeClass
    public void setup() {
        baseURI="http://localhost";
        port = 8080;
        basePath="/api";
    }
    @Test
    public void testHealthCheck(){
        given()
                .when()
                .get(REQUEST_PATH+"/health-check")
                .then().statusCode(200).extract().response().prettyPrint();
    }
    @Test
    public void testCreateTodo(){
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "id": 1,
                          "title": "Buy groceries",
                          "description": "Milk, Bread, Butter",
                          "completed": false
                        }
                        """)
                .when()
                .post(REQUEST_PATH)
                .then().statusCode(201)
                .extract()
                .response()
                .prettyPrint();
        countId++;
    }
}
