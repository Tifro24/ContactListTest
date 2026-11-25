package com.portfolio.steplibrary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.portfolio.models.CreateUserRequest;
import com.portfolio.models.CreateUserResponse;
import com.portfolio.models.UserLoginRequest;
import com.portfolio.utils.RegistrationUtils;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationStepLibrary {
    private final RegistrationUtils registrationUtils;

    public RegistrationStepLibrary(
            RegistrationUtils registrationUtils) {

        this.registrationUtils = registrationUtils;
    }
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response;
    String baseUrl = "https://thinking-tester-contact-list.herokuapp.com/";

    public CreateUserRequest createUserRequest(String firstName, String lastName){
        return CreateUserRequest.builder().
                firstName(firstName)
                .lastName(lastName)
                .email(randomEmail())
                .password("validPassword")
                .build();

    }

    public void postCreateUserRequest(CreateUserRequest createdUser, String urlSuffix) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String targetUrl = baseUrl + urlSuffix;
        String jsonPayload = mapper.writeValueAsString(createdUser);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response Body: " + response.body());
    }

    public void validateCreatedUserResponse(String firstName, String lastName, String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CreateUserResponse actualResponse = mapper.readValue(response.body(), CreateUserResponse.class);

        assertEquals(firstName, actualResponse.getUser().getFirstName());
        assertEquals(lastName, actualResponse.getUser().getLastName());
        assertEquals(email, actualResponse.getUser().getEmail());
    }

    public void validateReturnedToken(boolean expectToken) throws JsonProcessingException {
        String responseBody = response.body();
        if(responseBody == null || responseBody.isBlank()){
            assertFalse(expectToken, "Expected an authentication token but the response body was empty");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        String actualToken = jsonResponse.path("token").asText("");
        if(expectToken){
            assertFalse(actualToken.isBlank(), "Expected a non-empty authentication token");
        }
        else{
            assertTrue(actualToken.isBlank(), "Expected an empty authentication token");
        }

    }

    public void validateStatusCode(int statusCode){
        assertEquals(statusCode, response.statusCode());
    }

    public String randomEmail(){
        return registrationUtils.createRandomEmail();
    }

    public UserLoginRequest createUserLoginRequest(String email, String password, boolean invalid){
       if(invalid){
           return UserLoginRequest.builder().email(email).password("falsePassword!").build();
       }
       else{
           return UserLoginRequest.builder().email(email).password(password).build();

       }
    }

    public CreateUserResponse postUserLoginRequest(UserLoginRequest userLoginRequest) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String targetUrl = baseUrl + "users/login";
        String jsonPayload = mapper.writeValueAsString(userLoginRequest);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        CreateUserResponse actualResponse = mapper.readValue(response.body(), CreateUserResponse.class);
        System.out.println("Response Body: " + response.body());
        return actualResponse;
    }

    public String extractToken() throws JsonProcessingException {
        String responseBody = response.body();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        return jsonResponse.path("token").asText("");
    }
}
