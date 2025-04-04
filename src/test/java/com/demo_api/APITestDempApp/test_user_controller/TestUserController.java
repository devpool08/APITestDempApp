package com.demo_api.APITestDempApp.test_user_controller;

import static io.restassured.RestAssured.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.demo_api.APITestDempApp.entity.User;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

@SuppressWarnings("deprecation")
public class TestUserController {
    private static final String REQUEST_PATH="/user";
    @BeforeClass
    public void setup() {
        baseURI="http://localhost";
        port = 8080;
        basePath="/api";
    }
    @Test
    public void testHealthCheck() {
        get("/hello").
                then().
                statusCode(HttpStatus.SC_OK).
                extract().
                response().
                prettyPrint();
    }
    @Test(dependsOnMethods = "testHealthCheck")
    public void testCreatingAUser() {
        given().
                contentType(ContentType.JSON).
                body("""
                        {
                          "name": "John Doe",
                          "age": 30,
                          "email": "john.doe@demo.com",
                          "password": "securepassword123"
                        }
                        """).
                when().
                post(REQUEST_PATH).
                then().
                statusCode(HttpStatus.SC_CREATED).
                extract().
                response().
                prettyPrint();
    }
    @Test(invocationCount = 3,dependsOnMethods = "testCreatingAUser")
    public void testCreatingRandomDemoUser() {
        User pojo = new User(
                randomAlphabetic(3),
                new Random().nextInt(),
                randomAlphabetic(6)+"@dom.com",
                randomAlphabetic(9));
        given().
                contentType(ContentType.JSON).
                body(pojo).
                when().
                post(REQUEST_PATH).
                then().
                statusCode(HttpStatus.SC_CREATED).
                extract().
                response().
                prettyPrint();
    }
    @Test(dependsOnMethods = "testCreatingRandomDemoUser")
    public void testGettingParticularUser() {
        User pojo =
                get(REQUEST_PATH+"/1").
                        then().
                        statusCode(HttpStatus.SC_OK).
                        extract().
                        response().
                        as(User.class);
        System.out.println(pojo.toString());
    }
    @Test(dependsOnMethods = "testGettingParticularUser")
    public void testUpdatingUser() {
        User pojo = new User(
                randomAlphabetic(3),
                new Random().nextInt(),
                randomAlphabetic(6)+"@dom.com",
                randomAlphabetic(9));
        given().
                contentType(ContentType.JSON).
                body(pojo).
                when().
                put(REQUEST_PATH+"/1").
                then().
                statusCode(HttpStatus.SC_OK);
    }
    @Test(dependsOnMethods = "testUpdatingUser")
    public void testDeletingUser() {
        given().
                when().
                delete(REQUEST_PATH+"/1").
                then().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }
    @Test(dependsOnMethods = "testDeletingUser")
    public void testGetAllTheUser() {
        given().
                when().
                get(REQUEST_PATH).
                then().
                statusCode(HttpStatus.SC_OK).
                extract().
                response().
                prettyPrint();
    }
    @Test(dependsOnMethods = "testGetAllTheUser")
    public void testClearDB() {
        given().
                when().
                get("/clear").
                then().
                statusCode(HttpStatus.SC_NO_CONTENT).
                extract().
                response().
                prettyPrint();
    }
}