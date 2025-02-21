package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void testUserCreatesBuildType_Returns200_WhenTypeNotExists() {
        step("Create user", () -> {
            User newUser = TestDataGenerator.generate(User.class);

            CheckedBase<User> requester = new CheckedBase<>(Specifications.superUserAuth(), Endpoint.USERS);
            requester.create(newUser);
        });
        step("Create project by user");
        step("Create buildType in project by user");
        step("Check buildtype was created successfully");

    }

    @Test(description = "User should not be able to create build type with not unique id", groups = {"Negative", "CRUD"})
    public void testUserCreatesBuildType_Returns000_WhenTypeIdNotUnique() {
        step("Create user");
        step("Create project by user");
        step("Create buildType1 in project by user");
        step("Create buildType2 in project by user with the same id");
        step("Check buildtype2 was not created with statusCode 401");

    }

    @Test(description = "Project admin should be able to create build type", groups = {"Positive", "Roles"})
    public void testProjectAdminCreatesBuildType_Returns000_WhenTypeNotExists() {
        step("Create user");
        step("Create project by user");
        step("Grant user PROJECT_ADMIN role in project");
        step("Create buildType in project by user(PROJECT_ADMIN)");
        step("Check buildtype was created successfully");

    }

    @Test(description = "Project admin should not be able to create build type for another project", groups = {"Negative", "Roles"})
    public void testProjectAdminCreatesBuildType_Returns000_ForNotTheirProject() {
        step("Create user1");
        step("Create project1 by user1");
        step("Grant user1 PROJECT_ADMIN role in project1");

        step("Create user2");
        step("Create project2 by user2");

        step("Create buildType in project2 by user1(PROJECT_ADMIN)");
        step("Check buildtype was not created with statusCode 403");

    }
}
