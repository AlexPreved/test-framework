package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Step;

public class BaseApiTest extends BaseTest {
    @Step("Create a new project by user")
    protected Project createProjectByUser(User user) {
        Project project = TestDataGenerator.generate(Project.class);
        CheckedBase<Project> requester = new CheckedBase<>(Specifications.authSpec(user), Endpoint.PROJECTS);
        return requester.create(project);
    }

    @Step("Create a new user")
    protected User createUser() {
        User newUser = TestDataGenerator.generate(User.class);
        CheckedBase<User> requester = new CheckedBase<>(Specifications.superUserAuthSpec(), Endpoint.USERS);
        requester.create(newUser);
        return newUser;
    }

    @Step("Create a new build type in project by user")
    protected BuildType createBuildTypeInProjectByUser(User user, Project project) {
        BuildType buildType = TestDataGenerator.generate(BuildType.class);
        buildType.setProject(Project.builder().id(project.getId()).locator(null).build());
        CheckedBase<BuildType> requester = new CheckedBase<>(Specifications.authSpec(user), Endpoint.BUILD_TYPES);
        return requester.create(buildType);
    }
}
