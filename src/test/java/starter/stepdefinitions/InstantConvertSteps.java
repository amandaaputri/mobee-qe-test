package starter.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.admin.InstantConvert;

public class InstantConvertSteps {

    @Steps
    InstantConvert instantConvert;

    @Given("I set API endpoint for Instant Convert")
    public void setInstantConvertApiEndpoint() {
        instantConvert.setInstantConvertGetEndpoint();
    }

    @When("I send a GET request to Instant Convert endpoint")
    public void sendGetInstantConvertRequest() {
        instantConvert.sendGetInstantConvertRequest();
    }

    @When("I send a POST request with amount below minimum")
    public void sendPostWithAmountBelowMinimum() {
        instantConvert.sendPostWithAmountBelowMinimum();
    }

    @When("I send a POST request with amount above maximum")
    public void sendPostWithAmountAboveMaximum() {
        instantConvert.sendPostWithAmountAboveMaximum();
    }

    @When("I send a POST request with negative amount")
    public void sendPostWithNegativeAmount() {
        instantConvert.sendPostWithNegativeAmount();
    }

    @When("I send a POST request with unsupported currency pair")
    public void sendPostWithUnsupportedCurrencyPair() {
        instantConvert.sendPostWithUnsupportedCurrencyPair();
    }

    @When("I send a POST request with zero amount")
    public void sendPostWithZeroAmount() {
        instantConvert.sendPostWithZeroAmount();
    }

    @Then("I receive status code {int}")
    public void verifyStatusCode(int statusCode) {
        instantConvert.verifyStatusCode(statusCode);
    }

    @And("I get valid data for Instant Convert")
    public void getValidData() {
        instantConvert.validateResponseData();
    }

    @And("I receive error message {string}")
    public void verifyErrorMessage(String message) {
        instantConvert.verifyErrorMessage(message);
    }
}
