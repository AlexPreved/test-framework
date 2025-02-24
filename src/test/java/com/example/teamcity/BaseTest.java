package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    protected SoftAssert softAssert;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserAuthSpec());
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softAssert = new SoftAssert();
        testData = TestDataGenerator.generate();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softAssert.assertAll();
    }
}
