package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class DummyTest extends BaseTest {
    @Test
    public void unauthUser() {
        RestAssured.
                given().
                spec(Specifications.unAuthSpec()).
                get("/app/rest/projects");
    }

    @Test
    public void userShouldBeAbleGetAllProjects() {
        RestAssured.
                given().
                spec(Specifications.authSpec(User.builder().username("admin").password("admin").build())).
                get("/app/rest/projects");
    }
}
