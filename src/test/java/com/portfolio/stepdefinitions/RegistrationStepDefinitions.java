package com.portfolio.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolio.context.ScenarioContext;
import com.portfolio.models.CreateUserRequest;
import com.portfolio.models.UserLoginRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import com.portfolio.steplibrary.RegistrationStepLibrary;

import java.io.IOException;

public class RegistrationStepDefinitions {

    private final RegistrationStepLibrary registrationStepLibrary;
    private final ScenarioContext scenarioContext;

    public RegistrationStepDefinitions(
            RegistrationStepLibrary registrationStepLibrary,
            ScenarioContext scenarioContext) {

        this.registrationStepLibrary = registrationStepLibrary;
        this.scenarioContext = scenarioContext;
    }
    CreateUserRequest createUserRequest;
    UserLoginRequest userLoginRequest;

    @Given("I have valid and unique registration details")
    public void prepareRegistrationDetails() {
        createUserRequest = registrationStepLibrary.createUserRequest("John", "Doe");
    }

    @Given("I have an existing user")
    public void iHaveAnExistingUser() throws IOException, InterruptedException {
        createUserRequest = registrationStepLibrary.createUserRequest("John", "Doe");
        registrationStepLibrary.postCreateUserRequest(createUserRequest, "users");
    }

    @When("I register a new user")
    public void registerNewUser() throws IOException, InterruptedException {
        registrationStepLibrary.postCreateUserRequest(createUserRequest, "users");
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        registrationStepLibrary.validateStatusCode(expectedStatusCode);
    }

    @Then("the returned user details should match the submitted details")
    public void validateUserDetails() throws JsonProcessingException {
        registrationStepLibrary.validateCreatedUserResponse(createUserRequest.getFirstName(), createUserRequest.getLastName(), createUserRequest.getEmail());

    }

    @Then("a valid authentication token {string} returned")
    public void validateAuthenticationToken(String expectToken) throws JsonProcessingException {
        registrationStepLibrary.validateReturnedToken(expectToken.equalsIgnoreCase("is"));

    }

    @When("I login using {string} user details")
    public void iLoginUsingInvalidUserDetails(String validDetails) throws IOException, InterruptedException {
        userLoginRequest = registrationStepLibrary.createUserLoginRequest(createUserRequest.getEmail(), createUserRequest.getPassword(), validDetails.equalsIgnoreCase("invalid"));
        registrationStepLibrary.postUserLoginRequest(userLoginRequest);
    }

    @And("I store the user registration details")
    public void iStoreTheUserRegistrationDetails() throws JsonProcessingException {
        scenarioContext.setEmail(createUserRequest.getEmail());
        scenarioContext.setPassword(createUserRequest.getPassword());
        scenarioContext.setAuthenticationToken(registrationStepLibrary.extractToken());
    }
}

