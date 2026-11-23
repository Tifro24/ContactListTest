package com.portfolio.hooks;

import com.portfolio.context.ScenarioContext;
import io.cucumber.java.After;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestHooks {
    private final ScenarioContext context;

    public TestHooks(ScenarioContext context){
        this.context = context;
    }
    String baseUrl = "https://thinking-tester-contact-list.herokuapp.com/";
    HttpResponse<String> response;
    HttpClient client = HttpClient.newHttpClient();

    @After("@registration")
    public void deleteUser() throws IOException, InterruptedException {
        if(context.getAuthenticationToken() != null){
            String targetUrl = baseUrl + "users/me";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + context.getAuthenticationToken())
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Clean Up Status: " + response.body());
            if(response.statusCode() != 200){
                System.err.println("System clean up failed with HTTP status: " + response.statusCode());
            }
        }
    }
}
