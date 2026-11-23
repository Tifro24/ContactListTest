package com.portfolio.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScenarioContext {
    String email;
    String password;
    String authenticationToken;
    String loginToken;
}