package com.portfolio.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolio.context.ScenarioContext;
import com.portfolio.models.*;
import com.portfolio.steplibrary.ContactStepLibrary;
import com.portfolio.steplibrary.RegistrationStepLibrary;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class ContactStepDefinitions {
    private final ScenarioContext scenarioContext;
    private final ContactStepLibrary contactStepLibrary;
    private final RegistrationStepLibrary registrationStepLibrary;
    private CreateContactRequest createContactRequest;
    private CreateUserResponse createUserResponse;


    public ContactStepDefinitions(ContactStepLibrary contactStepLibrary,
                                  ScenarioContext scenarioContext,
                                  RegistrationStepLibrary registrationStepLibrary) {
        this.contactStepLibrary = contactStepLibrary;
        this.scenarioContext = scenarioContext;
        this.registrationStepLibrary = registrationStepLibrary;
    }

    @Given("a registered user exists and is logged in")
    public void aRegisteredUserExistsAndIsLoggedIn() throws IOException, InterruptedException {
        CreateUserRequest createUserRequest = registrationStepLibrary.createUserRequest("John", "Doe");
        registrationStepLibrary.postCreateUserRequest(createUserRequest, "users");
        scenarioContext.setEmail(createUserRequest.getEmail());
        scenarioContext.setPassword(createUserRequest.getPassword());
        scenarioContext.setAuthenticationToken(registrationStepLibrary.extractToken());
        UserLoginRequest userLoginRequest = registrationStepLibrary.createUserLoginRequest(createUserRequest.getEmail(), createUserRequest.getPassword(), false);
        createUserResponse = registrationStepLibrary.postUserLoginRequest(userLoginRequest);
    }

    @Given("I create a new contact")
    public void iCreateANewContact() throws IOException, InterruptedException {
        createContactRequest = contactStepLibrary.createContactRequest("Bikembe", "Bongomo");
        contactStepLibrary.postCreateContactRequest(createContactRequest);
    }


    @And("the returned contact details should match the submitted details")
    public void theReturnedContactDetailsShouldMatchTheSubmittedDetails() throws JsonProcessingException {
        contactStepLibrary.validateCreatedContactResponse(createContactRequest);
    }

    @When("I retrieve contact details")
    public void iRetrieveContactDetails() throws IOException, InterruptedException {
        contactStepLibrary.getContactRequest();
    }

    @Then("I store the session token")
    public void iStoreTheSessionToken(){
        scenarioContext.setSessionToken(createUserResponse.getToken());
    }

    @Then("I store the contact id")
    public void iStoreTheContactId() throws JsonProcessingException {
        scenarioContext.setContactId(contactStepLibrary.extractCreatedContactId());
    }

    @Then("the response status code is {int}")
    public void validateStatusCode(int expectedStatusCode) {
        contactStepLibrary.validateStatusCode(expectedStatusCode);
    }
}
