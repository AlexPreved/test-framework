package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AllProjectsPage extends BasePage {
    public static final String PROJECTS_URL = "/favorite/projects";

    private ElementsCollection projectElements = $$("div[class*='Subproject__container']");

    private SelenideElement header = $(".MainPanel__router--gF > div");

    public AllProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public static AllProjectsPage open() {
        return Selenide.open(PROJECTS_URL, AllProjectsPage.class);
    }

    public List<ProjectElement> getProjects() {
        return generatePageElement(projectElements, ProjectElement::new);
    }
}
