package ru._x100.cucumber.autotests.stepdefintions;

import cucumber.api.java.en.Then;
import env.DriverUtil;
import info.seleniumcucumber.stepdefinitions.PredefinedStepDefinitions;
import org.openqa.selenium.WebDriver;

public class RuPredefinedStepDefinitions extends PredefinedStepDefinitions {

    @Then("^перехожу на \"([^\"]*)\"$")
    public void i_navigate_to(String link) {
        navigationObj.navigateTo(link);
    }

    @Then("^закрываю браузер$")
    public void i_close_browser() {
        navigationObj.closeDriver();
    }
}
