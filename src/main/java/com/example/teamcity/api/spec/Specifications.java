package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class Specifications {
    private static volatile Specifications instance;

    private Specifications() {
    }

    public static Specifications getInstance() {
        if (instance == null) {
            synchronized (Specifications.class) {
                if (instance == null) {
                    instance = new Specifications();
                }
            }
        }
        return instance;
    }

    private RequestSpecBuilder reqSpecBuilder() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        requestSpecBuilder.setBaseUri("http://" + Config.getProperty("host"));
        return requestSpecBuilder;
    }

    public RequestSpecification unAuthSpec() {
        return reqSpecBuilder().build();
    }

    public RequestSpecification authSpec(User user) {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(user.getUser());
        basicAuthScheme.setPassword(user.getPassword());
        return reqSpecBuilder().setAuth(basicAuthScheme).build();
    }
}
