package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

/*
 * Содержит уникальные параметры конкретного запроса: кем слать(токен авторизации), relative url, model
 */
public class Request {
    protected final RequestSpecification specification;
    protected final Endpoint endpoint;

    public Request(RequestSpecification specification, Endpoint endpoint) {
        this.specification = specification;
        this.endpoint = endpoint;
    }
}
