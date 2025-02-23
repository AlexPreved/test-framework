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
    public Response read(String id) {
        return RestAssured.
                given().
                spec(specification).
                get(endpoint.getUrl() + "/id:" + id);
    }

    @Override
    public Response update(String id, BaseModel requestModel) {
        return RestAssured.
                given().
                spec(specification).
                body(requestModel).
                put(endpoint.getUrl() + "/id:" + id);
    }

    @Override
    public Response delete(String id) {
        return RestAssured.
                given().
                spec(specification).
                delete(endpoint.getUrl() + "/id:" + id);
    }
}
