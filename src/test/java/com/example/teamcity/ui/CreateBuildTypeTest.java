package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.admin.CreateBuildPage;
import lombok.val;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

public class CreateBuildTypeTest extends BaseUiTest {

    @Test(description = "User should be able to craete build configuration", groups = {"Positive"})
    public void testUserCreatesBuildConfiguration_notCreated_WhenProjectExists() {
        //создание проекта
        superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).create(testData.getProject());
        // авторизация
        loginAs(testData.getUser());

        step("Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL)");
        step("Click `Proceed`");
        step("Fix Project Name and Build Type name values");
        step("Click `Proceed`");
        CreateBuildPage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        step("Check that all entities (project, build type) was successfully created with correct data on API level");
        val createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softAssert.assertNotNull(createdBuildType);
    }
}
