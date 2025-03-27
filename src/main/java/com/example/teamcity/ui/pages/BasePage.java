package com.example.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BasePageElement;

import java.time.Duration;
import java.util.function.Function;
import java.util.List;

public abstract class BasePage {
    protected static final Duration BASE_WAITING = Duration.ofSeconds(30);

    protected <T extends BasePageElement> List<T> generatePageElement(ElementsCollection collection,
                                                                      Function<SelenideElement, T> creator) {
        // ElementCollection: Selenide Element 1, Selenide Element 2 и тд
        // collection.stream() -> Конвеер: Selenide Element 1, Selenide Element 2 и тд
        // creator(Selenide Element 1) -> T -> add to list
        // creator(Selenide Element 2) -> T -> add to list
        return collection.stream().map(creator).toList();
    }
}
