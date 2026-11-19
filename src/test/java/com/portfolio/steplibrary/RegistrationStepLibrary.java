package com.portfolio.steplibrary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.portfolio.models.CreateUserRequest;
import com.portfolio.models.CreateUserResponse;
import com.portfolio.utils.RegistrationUtils;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.fasterxml.jackson.databind.ObjectMapper;

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
                .password("fakePassword")
                .build();

    }

    public void postUserRequest(CreateUserRequest createdUser, String urlSuffix) throws IOException, InterruptedException {

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

    public void validateReturnedToken() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CreateUserResponse actualResponse = mapper.readValue(response.body(), CreateUserResponse.class);
        assertNotNull(actualResponse.getToken());
    }

    public void validateStatusCode(int statusCode){
        assertEquals(statusCode, response.statusCode());
    }

    public String randomEmail(){
        return registrationUtils.createRandomEmail();
    }
}
