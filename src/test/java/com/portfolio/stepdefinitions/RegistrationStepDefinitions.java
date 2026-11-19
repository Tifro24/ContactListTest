package com.portfolio.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolio.models.CreateUserRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import com.portfolio.steplibrary.RegistrationStepLibrary;

import java.io.IOException;

public class RegistrationStepDefinitions {

    private final RegistrationStepLibrary registrationStepLibrary;

    public RegistrationStepDefinitions(
            RegistrationStepLibrary registrationStepLibrary) {

        this.registrationStepLibrary = registrationStepLibrary;
    }
    CreateUserRequest createUserRequest;

    @Given("I have valid and unique registration details")
    public void prepareRegistrationDetails() {
        createUserRequest = registrationStepLibrary.createUserRequest("John", "Doe");
    }

    @When("I register a new user")
    public void registerNewUser() throws IOException, InterruptedException {
        registrationStepLibrary.postUserRequest(createUserRequest, "users");
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        registrationStepLibrary.validateStatusCode(expectedStatusCode);
    }

    @Then("the returned user details should match the submitted details")
    public void validateUserDetails() throws JsonProcessingException {
        registrationStepLibrary.validateCreatedUserResponse(createUserRequest.getFirstName(), createUserRequest.getLastName(), createUserRequest.getEmail());

    }

    @Then("a valid authentication token should be returned")
    public void validateAuthenticationToken() throws JsonProcessingException {
        registrationStepLibrary.validateReturnedToken();
    }
}

