package ru._x100.cucumber.autotests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/html-report"},
        features = "classpath:features",
        glue = {"info.seleniumcucumber.stepdefinitions", "ru.cucumber.autotests.stepdefintions"})
public class CucumberRunner {
}
