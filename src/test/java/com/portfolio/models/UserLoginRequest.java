package com.portfolio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginRequest {
    private String email;
    private String password;
}