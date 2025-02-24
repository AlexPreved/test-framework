package com.example.teamcity.api.generators;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;


import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private final EnumMap<Endpoint, Set<String>> createdEntities;

    private TestDataStorage() {
        createdEntities = new EnumMap<>(Endpoint.class);
    }

    public static TestDataStorage getStorage() {
        synchronized (TestDataStorage.class) {
            if (testDataStorage == null) {
                testDataStorage = new TestDataStorage();
            }
        }
        return testDataStorage;
    }

    public void addCreatedEntity(Endpoint endpoint, String id) {
        if (id != null) {
            createdEntities.computeIfAbsent(endpoint, key -> new HashSet<>()).add(id);
        }
    }

    public void addCreatedEntity(Endpoint endpoint, BaseModel baseModel) {
        addCreatedEntity(endpoint, getEntityIdOrLocator(baseModel));
    }

    public void deleteCreatedEntities() {
        createdEntities.forEach(((endpoint, ids) ->
                ids.forEach(id ->
                        new UncheckedBase(Specifications.superUserAuthSpec(), endpoint).delete(id))));
        createdEntities.clear();
    }

    private String getEntityIdOrLocator(BaseModel model) {
        try {
            var idField = model.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            var idFieldValue = Objects.toString(idField.get(model), null);
            idField.setAccessible(false);
            return idFieldValue;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                var idLocarotValue = model.getClass().getDeclaredField("locator");
                idLocarotValue.setAccessible(true);
                var idFieldValue = Objects.toString(idLocarotValue.get(model), null);
                idLocarotValue.setAccessible(false);
                return idFieldValue;
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException("такая хуйня, а не код ейбогу", ex);
            }
        }
    }
}
