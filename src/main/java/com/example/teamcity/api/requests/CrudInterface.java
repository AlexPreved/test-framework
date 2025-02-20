package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;

public interface CrudInterface {
    Object create(BaseModel requestModel);
    Object read(String id);
    Object update(String id, BaseModel requestModel);
    Object delete(String id);
}
