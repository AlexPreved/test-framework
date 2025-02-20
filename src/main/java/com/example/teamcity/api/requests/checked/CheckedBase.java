package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface {
    private final UncheckedBase uncheckedBase;

    public CheckedBase(RequestSpecification specification, Endpoint endpoint) {
        super(specification, endpoint);
        this.uncheckedBase = new UncheckedBase(specification, endpoint);
    }

    @Override
    public T create(BaseModel requestModel) {
        return (T) uncheckedBase.create(requestModel)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getResponseModelClass());
    }

    @Override
    public T read(String id) {
        return (T) uncheckedBase.read(id)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getResponseModelClass());
    }

    @Override
    public T update(String id, BaseModel requestModel) {
        return (T) uncheckedBase.update(id, requestModel)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getResponseModelClass());
    }

    @Override
    public String delete(String id) {
        return uncheckedBase.delete(id)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
