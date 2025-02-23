package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void testUserCreatesBuildType_Returns200_WhenTypeNotExists() {
        User createdUser = createUser();
        Project responseProject = createProjectByUser(createdUser);
        BuildType buildType = createBuildTypeInProjectByUser(createdUser, responseProject);
        checkBuildTypeCreatedSuccessfully(createdUser, buildType);
    }

    @Step("Create a new user")
    private User createUser() {
        User newUser = TestDataGenerator.generate(User.class);
        CheckedBase<User> requester = new CheckedBase<>(Specifications.superUserAuthSpec(), Endpoint.USERS);
        requester.create(newUser);
        return newUser;
    }

    @Step("Create a new project by user")
    private Project createProjectByUser(User user) {
        Project project = TestDataGenerator.generate(Project.class);
        CheckedBase<Project> requester = new CheckedBase<>(Specifications.authSpec(user), Endpoint.PROJECTS);
        return requester.create(project);
    }

    @Step("Create a new build type in project by user")
    private BuildType createBuildTypeInProjectByUser(User user, Project project) {
        BuildType buildType = TestDataGenerator.generate(BuildType.class);
        buildType.setProject(Project.builder().id(project.getId()).locator(null).build());
        CheckedBase<BuildType> requester = new CheckedBase<>(Specifications.authSpec(user), Endpoint.BUILD_TYPES);
        return requester.create(buildType);
    }

    @Step("Check build type was created successfully")
    private void checkBuildTypeCreatedSuccessfully(User user, BuildType buildType) {
        CheckedBase<BuildType> requester = new CheckedBase<>(Specifications.authSpec(user), Endpoint.BUILD_TYPES);
        BuildType createdBuildType = requester.read(buildType.getId());
        softAssert.assertEquals(buildType.getName(), createdBuildType.getName(), "Build type name is not correct");
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
