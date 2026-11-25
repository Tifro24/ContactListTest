package com.portfolio.steplibrary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.context.ScenarioContext;
import com.portfolio.models.*;
import com.portfolio.utils.RegistrationUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContactStepLibrary {
    private final RegistrationUtils registrationUtils;
    private final ScenarioContext scenarioContext;
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response;
    String baseUrl = "https://thinking-tester-contact-list.herokuapp.com/";

    public ContactStepLibrary(
            RegistrationUtils registrationUtils,
            ScenarioContext scenarioContext) {
        this.registrationUtils = registrationUtils;
        this.scenarioContext = scenarioContext;

    }

    public CreateContactRequest createContactRequest(String firstName, String lastName){
        return CreateContactRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthdate(LocalDate.of(2009, 7,14))
                .email(registrationUtils.createRandomEmail())
                .phone("07548270377")
                .street1("first street")
                .street2("second street")
                .city("Big City")
                .stateProvince("Ogun State")
                .postalCode("NW2 4ED")
                .country("UK")
                .build();

    }

    public void postCreateContactRequest(CreateContactRequest createContactRequest) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String targetUrl = baseUrl + "contacts";
        String jsonPayload = mapper.writeValueAsString(createContactRequest);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + scenarioContext.getSessionToken() )
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response Body: " + response.body());
        System.out.println("Status Code: " + response.statusCode());
    }

    public void getContactRequest() throws IOException, InterruptedException {
        String targetUrl = baseUrl + "contacts/" + scenarioContext.getContactId();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Authorization", "Bearer " + scenarioContext.getSessionToken() )
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response Body: " + response.body());
    }

    public String extractCreatedContactId() throws JsonProcessingException {
        String responseBody = response.body();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        return jsonResponse.path("_id").asText("");
    }

    public void validateCreatedContactResponse(CreateContactRequest createContactRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        CreateContactResponse actualResponse = mapper.readValue(response.body(), CreateContactResponse.class);

        assertNotNull(actualResponse.get_id());
        assertEquals(createContactRequest.getFirstName(), actualResponse.getFirstName());
        assertEquals(createContactRequest.getLastName(), actualResponse.getLastName());
        assertEquals(createContactRequest.getBirthdate(), actualResponse.getBirthdate());
        assertEquals(createContactRequest.getEmail(), actualResponse.getEmail());
        assertEquals(createContactRequest.getPhone(), actualResponse.getPhone());
        assertEquals(createContactRequest.getStreet1(), actualResponse.getStreet1());
        assertEquals(createContactRequest.getStreet2(), actualResponse.getStreet2());
        assertEquals(createContactRequest.getCity(), actualResponse.getCity());
        assertEquals(createContactRequest.getStateProvince(), actualResponse.getStateProvince());
        assertEquals(createContactRequest.getPostalCode(), actualResponse.getPostalCode());
        assertEquals(createContactRequest.getCountry(), actualResponse.getCountry());

    }

    public void validateStatusCode(int statusCode){
        assertEquals(statusCode, response.statusCode());
    }
}
