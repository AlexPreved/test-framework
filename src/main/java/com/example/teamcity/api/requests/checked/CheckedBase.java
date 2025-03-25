package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
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
        var createdModel =  (T) uncheckedBase.create(requestModel)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getResponseModelClass());
        TestDataStorage.getStorage().addCreatedEntity(endpoint,createdModel);
        return createdModel;
    }

    @Override
    public T read(String locator) {
        return (T) uncheckedBase.read(locator)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getResponseModelClass());
    }

    @Override
    public T update(String locator, BaseModel requestModel) {
        return (T) uncheckedBase.update(locator, requestModel)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getResponseModelClass());
    }

    @Override
    public String delete(String locator) {
        return uncheckedBase.delete(locator)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
