package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedBase extends Request implements CrudInterface {
    public UncheckedBase(RequestSpecification specification, Endpoint endpoint) {
        super(specification, endpoint);
    }

    @Override
    public Response create(BaseModel requestModel) {
        return RestAssured.
                given().
                spec(specification).
                body(requestModel).
                post(endpoint.getUrl());
    }

    @Override
    public Response read(String locator) {
        return RestAssured.
                given().
                spec(specification).
                get(endpoint.getUrl() + "/" + locator);
    }

    @Override
    public Response update(String locator, BaseModel requestModel) {
        return RestAssured.
                given().
                spec(specification).
                body(requestModel).
                put(endpoint.getUrl() + "/" + locator);
    }

    @Override
    public Response delete(String locator) {
        return RestAssured.
                given().
                spec(specification).
                delete(endpoint.getUrl() + "/" + locator);
    }
}
