package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;

public class CreateBuildPage extends CreateBasePage {
    private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

    public static CreateBuildPage open(String projectId) {
        return Selenide.open(CREATE_URL_TEMPLATE.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildPage.class);
    }

    public CreateBuildPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public void setupBuildType(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
    }
}
